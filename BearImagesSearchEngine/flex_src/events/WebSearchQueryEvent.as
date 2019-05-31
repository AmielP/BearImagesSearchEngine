package events
{
	import flash.events.Event;
	
	public class WebSearchQueryEvent extends Event
	{
		private var _webSearchQuery:String;
		
		public function WebSearchQueryEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		public static const PASSWEBSEARCHQUERY:String = "passwebsearchquery";
		
		
		public function get webSearchQuery():String
		{
			return _webSearchQuery;
		}
		
		public function set webSearchQuery(value:String):void
		{
			_webSearchQuery = value;
		}
	}
}