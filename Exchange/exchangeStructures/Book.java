package exchangeStructures;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import orderSpecs.Price;
import orderSpecs.Side;
import orderTypes.RestingOrder;
import orderTypes.SweepingOrder;

/**
 * This class implements one side of the order book, either
 * bid or offer. When its sweep method is called, it traverses
 * its price levels to find matching orders for the sweeping
 * order. When a sweeping order can't be fully filled, it is 
 * sent to the other side of the book to become a resting 
 * order.
 */
public class Book {
	
	/**
	 * Data structure for holding price levels
	 */
	private TreeMap<Price,PriceLevel> _priceLevels;
	
	/** The parent market of this half-book */
	private Market _market;
	
	/** The side of this book */
	private Side _side;
	
	/** If this is a bid book, the other side is an offer book */
	private Book _otherSide;

	/**
	 * Create a new (half) book, either bid or offer
	 * 
	 * @param market The market to which this book belongs
	 * @param side The side of this book, buy or sell
	 */
	public Book( Market market, Side side ) {
		
		// Save market and side
		_market = market;
		_side = side;
		
		// Instantiate a new data structure for holding price levels
		_priceLevels = new TreeMap<Price,PriceLevel>( side.getComparator() );
	}

	/**
	 * @return Side.BUY if this order is a buy, otherwise Side.SELL
	 */
	public Side getSide() { return _side; }
	
	/**
	 * Returns a price level for the given price
	 * @param price for which a price level will be returned
	 * @return A price level
	 */
	private PriceLevel getOrMakePriceLevel( Price price ) {
		// Find the right price level
		PriceLevel priceLevel = _priceLevels.get( price );
		// If it doesn't exist, make a new price level
		if( priceLevel == null ) {
			priceLevel = new PriceLevel( price );
			_priceLevels.put( price, priceLevel );
		}
		return priceLevel; 
	}
		
	/**
	 * Makes a sweeping order into a resting order.
	 * 
	 * @param sweepingOrder Order to make into a resting order
	 */
	public void makeRestingOrder( SweepingOrder sweepingOrder ) throws Exception {
		// Instantiate a resting order
		RestingOrder restingOrder = new RestingOrder( sweepingOrder );
		// Add resting order to price level
		this.getOrMakePriceLevel( sweepingOrder.getPrice() ).makeRestingOrder( restingOrder );
		// Register the resting order with the exchange
		_market.getExchange().registerRestingOrder( restingOrder );		
	}
	
	/**
	 * Performs a sweep of price levels in this half of the order
	 * book.
	 *  
	 * @param sweepingOrder Order with which to sweep this book
	 */
	public void sweep( SweepingOrder sweepingOrder ) throws Exception {
		if( ( sweepingOrder == null ) || ( sweepingOrder.isFilled() ) )
			throw new Exception( "Bad sweeping order in call to sweep method of Book" );
		// If there are price levels to traverse, traverse them
		if( !_priceLevels.isEmpty() ) {
			// Set up for iterating over price levels
			Price fromKey = _priceLevels.firstKey();
			Price toKey = _priceLevels.lastKey();
			boolean fromInclusive = true;
			boolean toInclusive = true;
			NavigableMap<Price,PriceLevel> sm = _priceLevels.subMap(fromKey, fromInclusive, toKey, toInclusive);
			Iterator<Entry<Price,PriceLevel>> priceLevelIterator = sm.entrySet().iterator();
			while( priceLevelIterator.hasNext() ) {
				Entry<Price,PriceLevel> entry = priceLevelIterator.next();
				// Get the next price level
				PriceLevel priceLevel = entry.getValue();
				// If the sweeping order is not marketable for this price level,
				// stop iterating over price levels 
				if( this.getSide().getComparator().compare( sweepingOrder.getPrice(), priceLevel.getPrice() ) == -1 )
					break;
				// Sweep this price level, generating fills
				boolean removePriceLevel = priceLevel.sweep( _market, sweepingOrder );
				// Is this price level empty?
				if( removePriceLevel )
					// This price level is empty so remove it
					priceLevelIterator.remove();
			}
		}
		// If the sweeping order has not been completely filled,
		// the remainder becomes a resting order
		if( !sweepingOrder.isFilled() )
			// Get the other side of this book and tell it
			// to make a resting order out of the sweeping
			// order
			this.getOtherSide().makeRestingOrder( sweepingOrder );
	}
	
	/**
	 * @return Offer book if this is a bid book, and vice versa
	 */
	public Book getOtherSide() { return _otherSide; }

	/**
	 * @return Treemap of price levels
	 */
	public TreeMap<Price,PriceLevel> getPriceLevels() { return _priceLevels; }
	
	/**
	 * @param _offerBook Sets variable representing other side of the book 
	 *                   to this value
	 */
	protected void setOtherSide(Book _offerBook) { _otherSide = _offerBook; }
	
}