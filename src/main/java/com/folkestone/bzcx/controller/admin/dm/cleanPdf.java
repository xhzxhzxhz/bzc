package com.folkestone.bzcx.controller.admin.dm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFOperator;


public class cleanPdf {
	 public Boolean doIt( String inputFile, String outputFile)
		        throws IOException, COSVisitorException
		    {
		 Boolean bo = false;
		        // the document
		        PDDocument doc = null;
		        List<String> li = new ArrayList<>();
		        try
		        {
		            doc = PDDocument.load( inputFile );
//		            PDFTextStripper stripper=new PDFTextStripper("ISO-8859-1");
		            List<?> pages = doc.getDocumentCatalog().getAllPages();
		            for( int i=0; i<pages.size(); i++ )
		            {
		                PDPage page = (PDPage)pages.get( i );
		                PDStream contents = page.getContents();
		                PDFStreamParser parser = new PDFStreamParser(contents.getStream() );
		                parser.parse();
		                List<Object> tokens = parser.getTokens();
		                for( int j=0; j<tokens.size(); j++ )
		                {
		                    Object next = tokens.get( j );
		                    if( next instanceof PDFOperator )
		                    {
		                        PDFOperator op = (PDFOperator)next;
		                        //Tj and TJ are the two operators that display
		                        //strings in a PDF
		                       li.add(op.getOperation());
		                       if( op.getOperation().equals( "Tj" ) )
		                        {
		                        	System.out.println("Tj");
		                            //Tj takes one operator and that is the string
		                            //to display so lets update that operator
		                            COSString previous = (COSString)tokens.get( j-1 );
		                            String string = "";
		                            previous.reset();
		                            previous.append( string.getBytes("GBK") );
		                        }
		                        else if( op.getOperation().equals( "TJ" ) )
		                        {
		                            COSArray previous = (COSArray)tokens.get( j-1 );
		                            for( int k=0; k<previous.size(); k++ )
		                            {
		                                Object arrElement = previous.getObject( k );
		                                if( arrElement instanceof COSString )
		                                {
		                                    COSString cosString = (COSString)arrElement;
		                                    String string = "";
		                                   // string = string.replaceAll(strToFind, message);
		                                    cosString.reset();
		                                    cosString.append( string.getBytes("GBK") );
		                                }
		                            }
		                       }
		                    }
		                }
		                //now that the tokens are updated we will replace the
		                //page content stream.
		                PDStream updatedStream = new PDStream(doc);
		                OutputStream out = updatedStream.createOutputStream();
		                ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
		                tokenWriter.writeTokens( tokens );
		                page.setContents( updatedStream );
		            }
		            System.out.println(li.size());
		            if(li.size() > 0) {
		            	doc.save( outputFile );
		            }else {
		            	bo = true;
		            }
		        }
		        finally
		        {
		            if( doc != null )
		            {
		                doc.close();
		            }
		        }
		        return bo;
		    }
}
