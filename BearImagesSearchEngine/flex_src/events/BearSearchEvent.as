package events
{
	import flash.events.Event;
	
	import mx.controls.Text;
	
	public class BearSearchEvent extends Event
	{
		public static const SEARCH:String = "search";
		
		private var _webSearchQuery:Text;
		
		public function BearSearchEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}

		public function get webSearchQuery():Text
		{
			return _webSearchQuery;
		}

		public function set webSearchQuery(value:Text):void
		{
			_webSearchQuery = value;
		}
	}
}