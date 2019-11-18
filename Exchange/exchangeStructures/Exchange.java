package exchangeStructures;

import java.util.HashMap;

import messages.Cancel;
import messages.CancelRejected;
import messages.Cancelled;
import messages.RestingOrderConfirmation;
import orderSpecs.ClientOrderId;
import orderSpecs.MarketId;
import orderTypes.RestingOrder;
import orderTypes.SweepingOrder;

/**
 * Implements an exchange with markets that each contain an
 * offer book and a bid book. Communicates with clients
 * using its Comms link. (We fake that part. There are no
 * clients, so all of the messages sent by the exchange to
 * clients are just saved in lists inside the Comms link
 * and can be retrieved for unit testing purposes.)
 *
 */
public class Exchange {

	/** The comms link that will simulate communications with clients */
	private Comms _comms;
	
	/** A data structure that contains all resting orders indexed
	 * by their clientOrderId
	 */
	private HashMap<ClientOrderId,RestingOrder> _restingOrders;
	
	/** A hashMap of all markets indexed by their marketId fields */
	private HashMap<MarketId,Market> _markets;
	
	/**
	 * Instantiate a new exchange, create a comms link and data
	 * for market and resting orders
	 */
	public Exchange() {
		
		// Instantiate a new comms link for simulating
		// communication with clients
		_comms = new Comms();
		
		// ??
		// Make a space for keeping a list of all resting orders in
		// all markets
		_restingOrders = new HashMap<ClientOrderId,RestingOrder>();
		
		// Create a hash map for finding markets by their market ids
		_markets = new HashMap<MarketId,Market>();
	}
	
	/**
	 * Add this order to the global list of resting orders and
	 * tell the client that the client's sweeping order has
	 * become a resting order
	 * 
	 * @param restingOrder Order to add to global list of resting
	 *                     orders
	 */
	public void registerRestingOrder( RestingOrder restingOrder ) {
		// ??
		// Add this resting order to the global list of all resting
		// orders
		_restingOrders.put( restingOrder.getClientOrderId(), restingOrder );
		
		// Send client a message about this order
		RestingOrderConfirmation restingOrderConfirmation = new RestingOrderConfirmation( restingOrder );
		_comms.sendRestingOrderConfirmation( restingOrderConfirmation );
	}
	
	/** Add a new market, eg IBM
	 * @param market Market to add
	 */
	public void addMarket( Market market ) {
		_markets.put( market.getMarketId(), market );
	}
	
	/** This is the starting point for a sweep.
	 * 
	 * The sweeping order's market id will be used to locate
	 * the market. The sweeping order will then be passed to
	 * that market for a sweep.
	 * 
	 * @param sweepingOrder Order that will sweep the specified
	 *                      market
	 * @throws Exception 
	 */
	public void sweep( SweepingOrder sweepingOrder ) throws Exception {
		Market market = _markets.get( sweepingOrder.getMarketId() );
		market.sweep( sweepingOrder );
	}
	
	/** Cancel a resting order in the book
	 * 
	 * @param cancelMsg Message specifying which order to cancel
	 */
	public void cancel( Cancel cancelMsg ) {
		RestingOrder restingOrder = _restingOrders.get( cancelMsg.getClientOrderId() );
		if( restingOrder == null ) {
			_comms.sendCancelRejected( new CancelRejected( cancelMsg.getClientId(), cancelMsg.getClientOrderId() ) );
		} else {
			try {
				restingOrder.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
			_restingOrders.remove( cancelMsg.getClientOrderId() );
			_comms.cancelled( new Cancelled( cancelMsg.getClientId(), cancelMsg.getClientOrderId() ) );
		}
	}
	
	/**
	 * @return Return the communications link that is used to
	 *         simulate communications with clients
	 */
	public Comms getComms() { return _comms; }

	/** Remove resting order from global list of all resting orders
	 * 
	 * @param restingOrder Resting order to remove from list
	 */
	public void unregisterOrder(RestingOrder restingOrder) {
		// ??
		_restingOrders.remove( restingOrder.getClientOrderId() );
	}

	/** 
	 * @return Map of all markets indexed by their market ids
	 */
	public HashMap<MarketId,Market> getMarkets() { return _markets; }

	/**
	 * @return Global map of resting orders indexed by their client
	 *         order ids
	 */
	public HashMap<ClientOrderId,RestingOrder> getRestingOrdersMap() { return _restingOrders; }

	/**
	 * Return the market object for a given market id
	 * @param marketId A market id such as "IBM"
	 * @return The market referenced by the above marketId
	 */
	public Market getMarket(MarketId marketId) {
		return this.getMarkets().get( marketId );
	}

	/**
	 * Retrieve a resting order by its client order id
	 * @param clientOrderId
	 * @return The resting order referenced by the above client order id
	 */
	public RestingOrder getOrder( ClientOrderId clientOrderId ) {
		return this.getRestingOrdersMap().get( clientOrderId );
	}

}