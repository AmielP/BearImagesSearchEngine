package components.bearSearchAdvDataGrids.utilities
{
	import interfaces.IRemotable;
	
	import mx.events.FlexEvent;
	import mx.rpc.remoting.RemoteObject;
	
	public class RemoteObjectWebSearch extends RemoteObjectSearch 
	{
		public function RemoteObjectWebSearch()
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
			remoteObject.destination = "gateway-webBearImageData";
		}
	}
}