package components.bearSearchAdvDataGrids.utilities
{
	import interfaces.IRemotable;
	
	import mx.controls.AdvancedDataGrid;
	import mx.events.FlexEvent;
	import mx.rpc.remoting.RemoteObject;
	
	public class RemoteObjectAdvancedSearch extends RemoteObjectSearch
	{
		public function RemoteObjectAdvancedSearch()
		{
			super();
		}
		
		override public function creationCompleteHandler(event:FlexEvent):void
		{
			initRemoteObject();
		}
		
		override public function initRemoteObject():void
		{
			super.initRemoteObject();
			remoteObject.destination = "gateway-advancedBearImageData";
		}
	}
}