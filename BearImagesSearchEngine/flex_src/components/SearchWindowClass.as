package components
{
	import components.base.BearSearchBase;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	import mx.rpc.AsyncToken;
	import mx.rpc.remoting.RemoteObject;
	
	import spark.components.Window;
	
	public class SearchWindowClass extends Window
	{
		public var bearSearchBase:BearSearchBase = new BearSearchBase();
		
		public function SearchWindowClass()
		{
			super();
		}
	}
}