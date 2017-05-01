/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psd.parser.layer.additional;
import java.io.IOException;

import psd.parser.PsdInputStream;
import psd.parser.layer.LayerAdditionalInformationParser;
import psd.parser.object.PsdDescriptor;

/**
 *
 * @author mohsinkhan
 */
public class LayerSolidColorParser implements LayerAdditionalInformationParser {
    public static final String TAG = "SoCo";
    
    	private final LayerSolidColorHandler handler;

	public LayerSolidColorParser(LayerSolidColorHandler  handler) {
		this.handler = handler;
	}
        
        	@Override
	public void parse(PsdInputStream stream, String tag, int size) throws IOException {
		int version = stream.readInt();
                
                PsdDescriptor descriptor = new PsdDescriptor(stream);
		
		if (handler != null) {
			handler.layerSolid(descriptor);
		}
	}
        
        
}





