package Tag;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Parser {
	
	private static String getFileExtension(File file)
	{
		String fileName = file.getName();
		int lastDot = fileName.lastIndexOf('.');
		return fileName.substring(lastDot + 1);
	}

	private static BufferedImage readImage(File file) throws IOException
	{
		ImageInputStream stream = null;
		BufferedImage image = null;
		try
		{
			stream = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
			if (readers.hasNext())
			{
				ImageReader reader = readers.next();
				reader.setInput(stream);
				image = reader.read(0);
			}
		}
		finally
		{
			if (stream != null)
			{
				stream.close();
			}
		}

		return image;
	}
	
	private static IIOMetadataNode createMetadata()
	{

		
		IIOMetadataNode animal = new IIOMetadataNode("Text");
		
		IIOMetadataNode name = new IIOMetadataNode("TextEntry");
		name.setAttribute("keyword", "heigAnimal");
	    name.setAttribute("value", "triton");
		animal.appendChild(name);
		
		/*IIOMetadataNode viewer = new IIOMetadataNode("heigViewer");
		viewer.appendChild(animal);*/
		
		IIOMetadataNode root = new IIOMetadataNode(IIOMetadataFormatImpl.standardMetadataFormatName);
		 //IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
		root.appendChild(animal);
		
		return root;
	}
	
	private static void writeImage(File outputFile, BufferedImage image, IIOMetadataNode newMetadata) throws IOException
	{
		String extension = getFileExtension(outputFile);
		ImageTypeSpecifier imageType = ImageTypeSpecifier.createFromBufferedImageType(image.getType());
		
		ImageOutputStream stream = null;
		try
		{
			Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix(extension);
			while(writers.hasNext())
			{
				ImageWriter writer = writers.next();
				ImageWriteParam writeParam = writer.getDefaultWriteParam();
				IIOMetadata imageMetadata = writer.getDefaultImageMetadata(imageType, writeParam);
				if (!imageMetadata.isStandardMetadataFormatSupported())
				{
					continue;
				}
				if (imageMetadata.isReadOnly())
				{
					continue;
				}
				
				//imageMetadata.mergeTree(IIOMetadataFormatImpl.standardMetadataFormatName, newMetadata);
				imageMetadata.mergeTree(IIOMetadataFormatImpl.standardMetadataFormatName, newMetadata);
				
				IIOImage imageWithMetadata = new IIOImage(image, null, imageMetadata);
				
				stream = ImageIO.createImageOutputStream(outputFile);
				writer.setOutput(stream);
				writer.write(null, imageWithMetadata, writeParam);
			}
		}
		finally
		{
			if (stream != null)
			{
				stream.close();
			}
		}
	}

	public static void main(String[] args)
	{
		
		try
		{
				File fileOrDirectory = new File("test.jpg");

				if (fileOrDirectory.isFile())
				{
					processFile(fileOrDirectory);
				}
				else
				{
					processDirectory(fileOrDirectory);
				}

			System.out.println("\nDone");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			File inputFile = new File("test.jpg");
			File outputFile = new File("test_new.jpg");
			
			BufferedImage image = readImage(inputFile);
			IIOMetadataNode newMetadata = createMetadata();
			writeImage(outputFile, image, newMetadata);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			
				File fileOrDirectory = new File("test_new.jpg");

				if (fileOrDirectory.isFile())
				{
					processFile(fileOrDirectory);
				}
				else
				{
					processDirectory(fileOrDirectory);
				}

			System.out.println("\nDone");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void indent(int level)
	{
		for (int i = 0; i < level; i++)
		{
			System.out.print("    ");
		}
	}

	private static void displayAttributes(NamedNodeMap attributes)
	{
		if (attributes != null)
		{
			int count = attributes.getLength();
			for (int i = 0; i < count; i++)
			{
				Node attribute = attributes.item(i);

				System.out.print(" ");
				System.out.print(attribute.getNodeName());
				System.out.print("='");
				System.out.print(attribute.getNodeValue());
				System.out.print("'");
			}
		}
	}

	private static void displayMetadataNode(Node node, int level)
	{
		indent(level);
		System.out.print("<");
		System.out.print(node.getNodeName());

		NamedNodeMap attributes = node.getAttributes();
		displayAttributes(attributes);

		Node child = node.getFirstChild();
		if (child == null)
		{
			String value = node.getNodeValue();
			if (value == null || value.length() == 0)
			{
				System.out.println("/>");
			}
			else
			{
				System.out.print(">");
				System.out.print(value);
				System.out.print("<");
				System.out.print(node.getNodeName());
				System.out.println(">");
			}
			return;
		}

		System.out.println(">");
		while (child != null)
		{
			displayMetadataNode(child, level + 1);
			child = child.getNextSibling();
		}

		indent(level);
		System.out.print("</");
		System.out.print(node.getNodeName());
		System.out.println(">");
	}
	
	private static void dumpMetadata(IIOMetadata metadata)
	{
		String[] names = metadata.getMetadataFormatNames();
		int length = names.length;
		for (int i = 0; i < length; i++)
		{
			indent(2);
			System.out.println("Format name: " + names[i]);
			displayMetadataNode(metadata.getAsTree(names[i]), 3);
		}
	}

	private static void processFileWithReader(File file, ImageReader reader) throws IOException
	{
		ImageInputStream stream = null;

		try
		{
			stream = ImageIO.createImageInputStream(file);

			reader.setInput(stream, true);

			IIOMetadata metadata = reader.getImageMetadata(0);
			
			indent(1);
			System.out.println("Image metadata");
			dumpMetadata(metadata);
			
			metadata = reader.getStreamMetadata();
			if (metadata != null)
			{
				indent(1);
				System.out.println("Stream metadata");
				dumpMetadata(metadata);
			}

		}
		finally
		{
			if (stream != null)
			{
				stream.close();
			}
		}
	}

	private static void processFile(File file) throws IOException
	{
		System.out.println("\nProcessing " + file.getName() + ":\n");

		String extension = getFileExtension(file);

		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(extension);

		while (readers.hasNext())
		{
			ImageReader reader = readers.next();

			System.out.println("Reader: " + reader.getClass().getName());

			processFileWithReader(file, reader);
		}
	}

	private static void processDirectory(File directory) throws IOException
	{
		System.out.println("Processing all files in " + directory.getAbsolutePath());

		File[] contents = directory.listFiles();
		for (File file : contents)
		{
			if (file.isFile())
			{
				processFile(file);
			}
		}
	}
	
	public void getMetadata(String arg) {
		
	}
}
