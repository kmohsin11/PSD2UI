package psd.model;

import java.io.File;
import java.io.IOException;

import psd.parser.layer.LayerParser;

public class Psd extends AbstractPsd<Layer> {

	public Psd(File psdFile) throws IOException {
		super(psdFile);
	}

	@Override
	protected Layer createLayer(LayerParser parser) {
		return new Layer(parser);
	}
}
