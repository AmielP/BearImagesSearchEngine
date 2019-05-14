package interfaces
{
	import mx.events.FlexEvent;

	public interface IHandler
	{
		function initializeHandler(event:FlexEvent):void;
		function creationCompleteHandler(event:FlexEvent):void;
	}
}