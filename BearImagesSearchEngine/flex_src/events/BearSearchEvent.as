package events
{
	import flash.events.Event;
	
	import mx.controls.Text;
	
	import spark.components.TextInput;
	
	public class BearSearchEvent extends Event
	{
		public static const SEARCH:String = "search";
		public static const DATA_SEARCH_CONFIRMED:String = "dataSearchConfirmed";
		
		private var _webSearchQuery:TextInput;
		
		public function BearSearchEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}

		public function get webSearchQuery():TextInput
		{
			return _webSearchQuery;
		}

		public function set webSearchQuery(value:TextInput):void
		{
			_webSearchQuery = value;
		}
	}
}