package components.base
{
	import flash.desktop.NativeApplication;
	import flash.events.FocusEvent;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.ui.Keyboard;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.controls.Alert;
	import mx.core.mx_internal;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.utils.StringUtil;
	
	import spark.components.Button;
	import spark.components.Group;
	import spark.components.TextInput;
	import spark.effects.Move;
	
	import components.bearSearchAdvDataGrids.advancedSearch.AdvancedSearchAdvDataGrid;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectSearch;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWebSearch;
	import components.bearSearchAdvDataGrids.webSearch.WebSearchAdvDataGrid;
	
	import interfaces.IFormattable;
	import interfaces.IHandler;
	import interfaces.IRemotable;
	import interfaces.ISearch;
	
	public class BearSearchBaseClass extends Group
	{
		// MXML Components must be declared public
		// This is a current limitation in this 
		// version of flex and may change in the
		// future.
		public var adgWebSearch:WebSearchAdvDataGrid = new WebSearchAdvDataGrid();
		public var adgAdvancedSearch:AdvancedSearchAdvDataGrid = new AdvancedSearchAdvDataGrid();
		
		[Bindable]
		public var tiSearch:TextInput = new TextInput();
		public var bSearch:Button = new Button();
		// Bindable button object in order to sustain the effects
		[Bindable]
		public var bPreviousState:Button = new Button();
		
		public function BearSearchBaseClass()
		{
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
		}
		
		// Creation complete is a good time to add event listeners
		// and interact with child components.
		public function creationCompleteHandler(event:FlexEvent):void
		{
			addEventListener(KeyboardEvent.KEY_DOWN, controlQHandler);
			bSearch.addEventListener(MouseEvent.CLICK, bSearch_clickHandler);
			tiSearch.addEventListener(FlexEvent.ENTER, tiSearch_enterHandler);
			
			bPreviousState.addEventListener(MouseEvent.CLICK, bpreviousState_clickHandler);
			
			adgWebSearch.addEventListener(ResultEvent.RESULT, webSearch_resultHandler);
			adgAdvancedSearch.addEventListener(ResultEvent.RESULT, advancedSearch_resultHandler);
		}
		
		private function controlQHandler(event:KeyboardEvent):void
		{
			if (event.ctrlKey && event.keyCode == 81)
			{
				NativeApplication.nativeApplication.exit();
			}
		}
		
		private function tiSearch_enterHandler(event:FlexEvent):void
		{
			bSearch_clickHandler(event as MouseEvent);
		}
		
		private function bSearch_clickHandler(event:MouseEvent):void
		{
			adgAdvancedSearch.acAdvancedSearch = null;
			adgAdvancedSearch.dataProvider = adgAdvancedSearch.acAdvancedSearch;
			if(tiSearch.text.length > 0)
			{
				adgWebSearch.search(tiSearch.text);
			}
			else
			{
				Alert.show("Please enter a search string", "Empty Textbox Error", 4, this);
			}
		}
		
		private function bpreviousState_clickHandler(event:MouseEvent):void{};
		
		private function webSearch_resultHandler(event:ResultEvent):void
		{
			adgAdvancedSearch.search(tiSearch.text);
		}
		
		private function advancedSearch_resultHandler(event:ResultEvent):void
		{
			PopUpManager.removePopUp(adgWebSearch.searchingAlert);
			PopUpManager.removePopUp(adgAdvancedSearch.searchingAlert);
			adgWebSearch.dataProvider = adgWebSearch.acWebSearch;
		}
	}
}