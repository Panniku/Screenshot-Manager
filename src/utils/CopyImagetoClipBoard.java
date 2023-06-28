package utils;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.*;
import java.io.IOException;

import window.Window;

import java.awt.*;


public class CopyImagetoClipBoard implements ClipboardOwner {
	public void copyImage(BufferedImage bi)
    {
        TransferableImage trans = new TransferableImage( bi );
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
        	c.setContents( trans, this );
        } catch (Exception e) {
        	//e.printStackTrace();
        	//Window.log("Failed to copy to clipboard.");
        	Window.getLogger().logE("Failed to copy to clipboard\n");
        }
    }

    public void lostOwnership( Clipboard clip, Transferable trans ) {
        //System.out.println( "Lost Clipboard Ownership" );
    }

    private class TransferableImage implements Transferable {

        Image i;

        public TransferableImage( Image i ) {
            this.i = i;
        }

        public Object getTransferData( DataFlavor flavor )
        throws UnsupportedFlavorException, IOException {
            if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
                return i;
            }
            else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor ) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for ( int i = 0; i < flavors.length; i++ ) {
                if ( flavor.equals( flavors[ i ] ) ) {
                    return true;
                }
            }

            return false;
        }
    }
}