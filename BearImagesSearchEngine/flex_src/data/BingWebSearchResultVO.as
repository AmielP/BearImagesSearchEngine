package data
{
	import flash.utils.ByteArray;

	public class BingWebSearchResultVO
	{
		private var _contentID:int;
		private var _webSearchUrl:String;
		private var _name:String;
		private var _thumbnailUrl:String;
		private var _datePublished:Date;
		private var _contentUrl:String;
		private var _hostPageUrl:String;
		private var _contentSize:String;
		private var _encodingFormat:String;
		private var _hostPageDisplayUrl:String;
		private var _width:int;
		private var _height:int;
		private var _thumbnail:String;
		private var _imageData:ByteArray;
		
		public function BingWebSearchResultVO()
		{
			
		}
		
		public function get contentID():int
		{
			return _contentID;
		}

		public function set contentID(value:int):void
		{
			_contentID = value;
		}

		public function get webSearchUrl():String
		{
			return _webSearchUrl;
		}

		public function set webSearchUrl(value:String):void
		{
			_webSearchUrl = value;
		}

		public function get name():String
		{
			return _name;
		}

		public function set name(value:String):void
		{
			_name = value;
		}

		public function get thumbnailUrl():String
		{
			return _thumbnailUrl;
		}

		public function set thumbnailUrl(value:String):void
		{
			_thumbnailUrl = value;
		}

		public function get datePublished():Date
		{
			return _datePublished;
		}

		public function set datePublished(value:Date):void
		{
			_datePublished = value;
		}

		public function get contentUrl():String
		{
			return _contentUrl;
		}

		public function set contentUrl(value:String):void
		{
			_contentUrl = value;
		}

		public function get hostPageUrl():String
		{
			return _hostPageUrl;
		}

		public function set hostPageUrl(value:String):void
		{
			_hostPageUrl = value;
		}

		public function get contentSize():String
		{
			return _contentSize;
		}

		public function set contentSize(value:String):void
		{
			_contentSize = value;
		}

		public function get encodingFormat():String
		{
			return _encodingFormat;
		}

		public function set encodingFormat(value:String):void
		{
			_encodingFormat = value;
		}

		public function get hostPageDisplayUrl():String
		{
			return _hostPageDisplayUrl;
		}

		public function set hostPageDisplayUrl(value:String):void
		{
			_hostPageDisplayUrl = value;
		}

		public function get width():int
		{
			return _width;
		}

		public function set width(value:int):void
		{
			_width = value;
		}

		public function get height():int
		{
			return _height;
		}

		public function set height(value:int):void
		{
			_height = value;
		}

		public function get thumbnail():String
		{
			return _thumbnail;
		}

		public function set thumbnail(value:String):void
		{
			_thumbnail = value;
		}

		public function get imageData():ByteArray
		{
			return _imageData;
		}

		public function set imageData(value:ByteArray):void
		{
			_imageData = value;
		}


	}
}