package interfaces
{
	public interface IFormattable extends IHandler
	{	
		function get amf():AMFClass;
		function set amf(value:AMFClass):void;
	}
}