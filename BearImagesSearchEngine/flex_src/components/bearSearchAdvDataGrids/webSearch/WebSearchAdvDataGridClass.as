package components.bearSearchAdvDataGrids.webSearch
{
	import components.bearSearchAdvDataGrids.sharedBehaviors.SearchAdvDataGridClass;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWebSearch;
	
	import interfaces.ISearch;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	import mx.utils.StringUtil;
	
	public class WebSearchAdvDataGridClass extends SearchAdvDataGridClass
	{	
		private var iSearch:ISearch = new RemoteObjectWebSearch();
		
		[Bindable]
		private var webSearchData:ArrayCollection;
//		public var dp:ArrayCollection;
//		private var dpWebSearch:ArrayCollection;
//		private var roWebSearch:RemoteObject = remotable.remoteObject;
//		private var myAmf:AMFClass = formattable.amf;
//		
//		
//		private var webStuff:SearchAdvDataGridClass = new WebSearchAdvDataGrid();
		
		public function WebSearchAdvDataGridClass()
		{
			super();
//			iSearch = new RemoteObjectWebSearch();
			addEventListener(FlexEvent.INITIALIZE, iSearch.initializeHandler);
//			addEventListener(FlexEvent.INITIALIZE, initializeHandler);
			addEventListener(FlexEvent.CREATION_COMPLETE, iSearch.creationCompleteHandler);
//			roWebSerch = new RemoteObjectWebSearch();
		}
		
//		private function initializeHandler(event:FlexEvent):void
//		{
//			parentApplication.addEventListener(WebSearchQueryEvent.PASSWEBSEARCHQUERY, passWebSearchQuery);
//		}
		
//		private function passWebSearchQuery(event:WebSearchQueryEvent):void
//		{
//			webSearch();
//		}
		
		public function webSearch(webSearchQuery:String, remoteObject:RemoteObject):void
		{
			if (!StringUtil.trim(webSearchQuery))
			{
				webSearchQuery = "bear";
			}
			trace("Remote object in WebSearchAdvDataGrid class: " + remoteObject);
			var token:AsyncToken = remoteObject.initiateWebSearch(webSearchQuery);
			var responder:AsyncResponder = new AsyncResponder(webSearch_resultHandler, webSearch_faultHandler, token);
			token.addResponder(responder);
		}

		public function webSearch_resultHandler(event:ResultEvent, token:Object):void
		{
			webSearchData = new ArrayCollection();
			webSearchData = event.result as ArrayCollection;
			dataProvider = webSearchData;
			trace("Success: webSearch_resultHandler reached");
//			Alert.show("Success: first image is " + webSearchData[0].contentUrl, "webSearch_resultHandler", 4, this);
		}
		
		public function webSearch_faultHandler(event:FaultEvent, token:Object):void
		{
			trace("webSearch_faultHandler: " + event);
			Alert.show("Failure: " + event, "webSearch_faultHandler", 4, this);
		}
//		private function webSearch():void
//		{
//			remotable = new RemoteObjectWebSearch();
//			webStuff.performFly();
//			webStuff.remotable = new RemoteObjectSearch();	
//				
//				
//				
//			roWebSearch = new RemoteObject();
//			roWebSearch.channelSet = myAmf.amfClassChannelSet;
//			roWebSearch.destination = "gateway-webBearImageData";
////			var token:AsyncToken = roWebSearch.initRemoteObject();
//		}
	}
}

//can delete this later if decoupling is completely fixed
//package components.bearSearchAdvDataGrids.webSearch
//{
//	import components.bearSearchAdvDataGrids.sharedBehaviors.SearchAdvDataGridClass;
//	
//	import mx.collections.ArrayCollection;
//	import mx.events.FlexEvent;
//	import mx.rpc.AsyncToken;
//	import mx.rpc.remoting.RemoteObject;
//
//	public class WebSearchAdvDataGridClass extends SearchAdvDataGridClass
//	{
//		private var roWebBearImageData:RemoteObject;
//		
//		private var dpWebSearch:ArrayCollection;
//		
//		public function WebSearchAdvDataGridClass()
//		{
//			super();
//		}
//		
//		override protected function initializeHandler(event:FlexEvent):void
//		{
//			amf = new AMFClass();
//			amf.initAMFClassChannel();
//		}
//		
//		override protected function creationCompleteHandler(event:FlexEvent):void
//		{
//			initRemoteObject();
//		}
//		
//		override protected function initRemoteObject():void
//		{
//			roBearImageData = new RemoteObject();
//			roBearImageData.channelSet = amf.amfClassChannelSet;
//			roBearImageData.destination = "gateway-webBearImageData";
//			roWebBearImageData = roBearImageData;
//		}
//	}
//}

//package
//{
//	import flash.events.MouseEvent;
//	
//	import mx.controls.Button;
//	import mx.controls.TextInput;
//	import mx.core.WindowedApplication;
//	import mx.events.FlexEvent;
//
//	[Bindable]
//	public class BearImagesSearchEngine extends WindowedApplication
//	{
//		public var bInitialSearch:Button = new Button();
//		public var bSearch:Button = new Button();
//		public var bPreviousState:Button = new Button();
//		public var tiSearch:TextInput = new TextInput();
//		
//		public function BearImagesSearchEngine()
//		{
//			super();
//			
//			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
//		}
//		
//		private function creationCompleteHandler(event:FlexEvent):void
//		{
//			bInitialSearch.addEventListener(MouseEvent.CLICK, bInitialSearchHandler);
//		}
//		
//		private function bInitialSearchHandler(event:MouseEvent):void
//		{
//			
//		}
//	}
//}