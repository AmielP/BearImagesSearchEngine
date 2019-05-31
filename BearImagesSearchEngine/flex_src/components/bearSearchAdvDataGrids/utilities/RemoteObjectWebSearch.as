package components.bearSearchAdvDataGrids.utilities
{
	import components.base.BearSearchBase;
	
	import events.BearSearchEvent;
	
	import interfaces.IRemotable;
	import interfaces.ISearch;
	
	import mx.collections.ArrayCollection;
	import mx.controls.AdvancedDataGrid;
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import spark.components.TextInput;
	
	//remote commented stuff if able to get stuff working goodly
	public class RemoteObjectWebSearch extends RemoteObjectSearch implements ISearch
	{
//		public var bearSearchBase:BearSearchBase = new BearSearchBase();
//		private var dpBingWebSearch:ArrayCollection;
		private var _remoteObject:RemoteObject;
		
		public function RemoteObjectWebSearch()
		{
			super();
			addEventListener(FlexEvent.INITIALIZE, initializeHandler);
			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
		}
		
		public function initializeHandler(event:FlexEvent):void
		{
//			super.initializeHandler(event);
			amf = new AMFClass();
			amf.initAMFClassChannel();
			
		}
		
		public function creationCompleteHandler(event:FlexEvent):void
		{
//			bearSearchBase.addEventListener(BearSearchEvent.SEARCH, searchHandler);
			//move this back if both FlexEvent.CREATION_COMPLETEs work in BearSearchBaseClass
			initRemoteObject();
			trace("remote object in creationCompleteHandler: " + _remoteObject);
		}
		
//		private function searchHandler(event:BearSearchEvent):void
//		{
//			webSearch(event.webSearchQuery);
//		}
		
//		private function webSearch_resultHandler(event:ResultEvent, token:Object):void
//		{
//			dpBingWebSearch = new ArrayCollection();
//			dpBingWebSearch = event.result as ArrayCollection;
//			trace("Success: Button Click Reached");
//		}
//		
//		private function webSearch_faultHandler(event:FaultEvent, token:Object):void
//		{
//			Alert.show("Failure:", "webSearch_faultHandler", 4, this);
//		}
//		
//		private function webSearch(textInput:TextInput):void
//		{
//			var token:AsyncToken = _remoteObject.initiateWebSearch(textInput.text);
//			var responder:AsyncResponder = new AsyncResponder(webSearch_resultHandler, webSearch_faultHandler, token);
//			token.addResponder(responder);
//		}
		
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
			_remoteObject = new RemoteObject();
			_remoteObject.channelSet = amf.amfClassChannelSet;
			_remoteObject.destination = "gateway-webBearImageData";
		}
	}
}