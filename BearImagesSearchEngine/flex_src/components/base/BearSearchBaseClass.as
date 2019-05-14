package components.base
{
	import components.bearSearchAdvDataGrids.advancedSearch.AdvancedSearchAdvDataGrid;
	import components.bearSearchAdvDataGrids.webSearch.WebSearchAdvDataGrid;
	
	import flash.events.MouseEvent;
	
	import interfaces.IHandler;
	
	import mx.events.FlexEvent;
	
	import spark.components.Button;
	import spark.components.Group;
	import spark.components.TextInput;
	
	public class BearSearchBaseClass extends Group implements IHandler
	{
		// MXML Components must be declared public
		// This is a current limitation in this 
		// version of flex and may change in the
		// future.
		public var adgWebSearch:WebSearchAdvDataGrid = new WebSearchAdvDataGrid();
		public var adgAdvancedSearch:AdvancedSearchAdvDataGrid = new AdvancedSearchAdvDataGrid();
		
		public var tiSearch:TextInput = new TextInput();
		public var bInitialSearch:Button = new Button();
		public var bSearch:Button = new Button();
		// Bindable button object in order to sustain the effects
		[Bindable]
		public var bPreviousState:Button = new Button();
		
		public function BearSearchBaseClass()
		{
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
		}
		
		public function initializeHandler(event:FlexEvent):void{};
		
		// Creation complete is a good time to add event listeners
		// and interact with child components.
		public function creationCompleteHandler(event:FlexEvent):void
		{
			bInitialSearch.addEventListener(MouseEvent.CLICK, initialSearchHandler);
			bSearch.addEventListener(MouseEvent.CLICK, searchHandler);
			bPreviousState.addEventListener(MouseEvent.CLICK, previousStateHandler);
		}
		
		private function initialSearchHandler(event:MouseEvent):void
		{
			//			includeIn = "initialSearch";
		}
		
		private function searchHandler(event:MouseEvent):void
		{
			
		}
		
		private function previousStateHandler(event:MouseEvent):void
		{
			
		}
	}
}