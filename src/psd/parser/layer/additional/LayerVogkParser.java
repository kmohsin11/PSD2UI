package psd.parser.layer.additional;

import java.io.IOException;

import psd.parser.PsdInputStream;
import psd.parser.layer.LayerAdditionalInformationParser;
import psd.parser.object.PsdDescriptor;

public class LayerVogkParser implements LayerAdditionalInformationParser {

	public static final String TAG = "vogk";
	private final LayerVogkHandler handler;
	
	public LayerVogkParser(LayerVogkHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void parse(PsdInputStream stream, String tag, int size) throws IOException {
		int eff = stream.readInt();
                int version = stream.readInt();
                PsdDescriptor descriptor = new PsdDescriptor(stream);
		if (handler != null) {
			handler.layerVogkParsed(descriptor);
		}
	}

}
