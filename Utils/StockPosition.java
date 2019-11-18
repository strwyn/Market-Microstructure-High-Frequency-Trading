package lecture2;

public class StockPosition {
	
	protected Stock  _stock;
	protected int    _quantity;
	protected long   _lastPrice;
	protected String _positionId;
	
	public StockPosition( Stock stock, String positionId ) {
		_stock = stock;
		_quantity = 0;
		_lastPrice = 0;
		_positionId = positionId;
	}
	
	public Stock getStock() { return _stock; }
	public int getQuantity() { return _quantity; }
	public long getPrice() { return _lastPrice; }
	public String getPositionId() { return _positionId; }
	
	public long markToMarket( long newPrice ) {
		long mtm = ( newPrice - this.getPrice() ) * _quantity;
		_lastPrice = newPrice;
		return mtm;
	}
	
	@Override
	public int hashCode() {
		return this.getPositionId().hashCode();
	}
	
	@Override
	public boolean equals( Object object ) {
		if( object == this )
			return true;
		return false;
	}

}
