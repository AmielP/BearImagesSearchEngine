package components.bearSearchAdvDataGrids.webSearch
{
	import components.bearSearchAdvDataGrids.sharedBehaviors.SearchAdvDataGridClass;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWebSearch;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	import mx.rpc.AsyncToken;
	import mx.rpc.remoting.RemoteObject;
	
	public class WebSearchAdvDataGridClass extends SearchAdvDataGridClass
	{	
		private var dpWebSearch:ArrayCollection;
		
		public function WebSearchAdvDataGridClass()
		{
			super();
			roBearImageData = new RemoteObjectWebSearch();
		}
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