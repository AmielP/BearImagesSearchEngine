package components.bearSearchAdvDataGrids.utilities
{
	import interfaces.IFormattable;
	
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import spark.components.Group;

	public class RemoteObjectSearch extends UIComponent implements IFormattable
	{
		private var _amf:AMFClass;
		
		public function RemoteObjectSearch()
		{}
		
		public function get amf():AMFClass
		{
			return _amf;
		}
		
		public function set amf(value:AMFClass):void
		{
			_amf = value;
		}
	}
}