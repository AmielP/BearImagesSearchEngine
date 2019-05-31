package components.bearSearchAdvDataGrids.utilities
{
	import interfaces.IFormattable;
	import interfaces.IRemotable;
	import interfaces.IRemoteObject;
	
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import spark.components.Window;
	
	public class RemoteObjectWindow extends Window implements IFormattable, IRemotable, IRemoteObject
	{
		private var _amf:AMFClass;
		private var _remoteObject:RemoteObject;
		
		public function RemoteObjectWindow()
		{
			super();
		}
		
		public function initializeHandler(event:FlexEvent):void{};
		
		public function creationCompleteHandler(event:FlexEvent):void{};
		
		public function get amf():AMFClass
		{
			return _amf;	
		}
		
		public function set amf(value:AMFClass):void
		{
			_amf = value;	
		}
		
		public function get remoteObject():RemoteObject
		{
			return _remoteObject;
		}
		
		public function set remoteObject(value:RemoteObject):void
		{
			_remoteObject = value;
		}
		
		public function initRemoteObject():void
		{
			remoteObject = new RemoteObject();
			remoteObject.channelSet = amf.amfClassChannelSet;
		}
	}
}