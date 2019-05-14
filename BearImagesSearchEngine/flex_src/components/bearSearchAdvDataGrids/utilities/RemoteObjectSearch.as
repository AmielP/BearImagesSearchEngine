package components.bearSearchAdvDataGrids.utilities
{
	import interfaces.IFormattable;
	import interfaces.IRemotable;
	import interfaces.IRemoteObject;
	
	import mx.controls.AdvancedDataGrid;
	import mx.events.FlexEvent;
	import mx.rpc.remoting.RemoteObject;
	
	public class RemoteObjectSearch extends AdvancedDataGrid implements IFormattable, IRemoteObject, IRemotable
	{
		private var _amf:AMFClass;
		private var _remoteObject:RemoteObject;
		
		public function RemoteObjectSearch()
		{
			super();
			addEventListener(FlexEvent.INITIALIZE, initializeHandler);
			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
		}
		
		public function initializeHandler(event:FlexEvent):void
		{
			amf = new AMFClass();
			amf.initAMFClassChannel();
		}
		
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