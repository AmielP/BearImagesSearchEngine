package
{
	import flash.events.MouseEvent;
	
	import mx.controls.Button;
	import mx.controls.TextInput;
	import mx.core.WindowedApplication;
	import mx.events.FlexEvent;

	[Bindable]
	public class BearImagesSearchEngine extends WindowedApplication
	{
		public var bInitialSearch:Button = new Button();
		public var bSearch:Button = new Button();
		public var bPreviousState:Button = new Button();
		public var tiSearch:TextInput = new TextInput();
		
		public function BearImagesSearchEngine()
		{
			super();
			
			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
		}
		
		private function creationCompleteHandler(event:FlexEvent):void
		{
			bInitialSearch.addEventListener(MouseEvent.CLICK, bInitialSearchHandler);
		}
		
		private function bInitialSearchHandler(event:MouseEvent):void
		{
			
		}
	}
}