/**
 * PRO
 * Authors: Bacso
 * File: Parser.java
 * IDE: NetBeans IDE 11
 */
package Tag;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.NodeList;

/**
 * Classe de gestion des metadatas d'images avec l'ajout et la lecture des tags.
 *
 * @author Bacso
 */
public class Parser {

    /**
     * Ajoute les tags sur une image ou sur toutes les images d'un répertoire
     *
     * @param tags Liste de tags
     * @param imagesPath Chemin d'une image ou d'un répertoire
     */
    public void setTags(ArrayList<String> tags, String imagesPath) {
        File inputFile = new File(imagesPath);
        if (inputFile.isFile()) {
            writeTag(inputFile, tags);
        } else if (inputFile.isDirectory()) {
            File[] contents = inputFile.listFiles();
            for (File file : contents) {
                if (file.isFile()) {
                    if (getFileExtension(file).equals("jpg")) {
                        writeTag(file, tags);
                    }
                }
            }
        }
    }

    /**
     * Ecrit les tags sur une image
     *
     * @param file Image à modifier
     * @param tags Liste de tags
     */
    private void writeTag(File file, ArrayList<String> tags) {
        ImageOutputStream output;
        try (ImageInputStream input = ImageIO.createImageInputStream(file)) {
            output = ImageIO.createImageOutputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            ImageReader reader = readers.next();
            reader.setInput(input);
            IIOImage image = reader.readAll(0, null);
            addTextEntry(image.getMetadata(), "heigViewer", tags);
            ImageWriter writer = ImageIO.getImageWriter(reader); // TODO: Validate that there are writers
            writer.setOutput(output);
            writer.write(image);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Récupère l'extension d'un fichier
     *
     * @param file Fichier
     * @return Retourne l'extension
     */
    private String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        return fileName.substring(lastDot + 1);
    }

    /**
     * Modifie les metadatas en ajoutant les tags. Si il y a déjà des tags
     * présents sur l'image on les supprimes et on ajoute les nouveaux.
     *
     * @param metadata Metadata de l'image
     * @param key Valeur unique définissant un tag
     * @param value Liste de tags
     * @throws IIOInvalidTreeException
     * @throws UnsupportedEncodingException
     */
    private void addTextEntry(final IIOMetadata metadata, final String key, final ArrayList<String> value) throws IIOInvalidTreeException, UnsupportedEncodingException {

        IIOMetadataNode tree = (IIOMetadataNode) metadata.getAsTree("javax_imageio_jpeg_image_1.0");

        IIOMetadataNode textNode = (IIOMetadataNode) tree.getElementsByTagName("markerSequence").item(0);
        NodeList entries = textNode.getElementsByTagName("com");

        for (int i = 0; i < entries.getLength(); i++) {
            IIOMetadataNode node = (IIOMetadataNode) entries.item(i);
            if (node.getAttribute("comment").startsWith(key)) {
                textNode.removeChild(node);
            }
        }

        for (int i = 0; i < value.size(); i++) {
            IIOMetadataNode textEntry = new IIOMetadataNode("com");
            try {
                textEntry.setUserObject((value.get(i)).getBytes("ISO-8859-1"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
            textNode.appendChild(textEntry);
        }

        metadata.setFromTree("javax_imageio_jpeg_image_1.0", tree);
    }

    /**
     * Récupère les tags présent sur l'image
     *
     * @param metadata Metadata de l'image
     * @param key Valeur unique définissant un tag
     * @return Liste des tags présents
     */
    public ArrayList<String> getTextEntry(final IIOMetadata metadata, final String key) {
        ArrayList<String> tags = new ArrayList<String>();
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(IIOMetadataFormatImpl.standardMetadataFormatName);
        NodeList entries = root.getElementsByTagName("TextEntry");

        for (int i = 0; i < entries.getLength(); i++) {
            IIOMetadataNode node = (IIOMetadataNode) entries.item(i);
            if (node.getAttribute("value").startsWith(key)) {
                tags.add(node.getAttribute("value"));
            }
        }

        return tags;
    }

    /**
     * Trouve la présence de tags sur l'image
     *
     * @param metadata Metadata de l'image
     * @param key Valeur unique définissant un tag
     * @return True si il y des tags sinon false
     */
    private boolean findTag(final IIOMetadata metadata, final String key) {
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(IIOMetadataFormatImpl.standardMetadataFormatName);
        NodeList entries = root.getElementsByTagName("TextEntry");
        if (entries == null) {
            entries = root.getElementsByTagName("com");
        }

        for (int i = 0; i < entries.getLength(); i++) {
            IIOMetadataNode node = (IIOMetadataNode) entries.item(i);
            if (node.getAttribute("value").startsWith(key)) {
                return true;
            }

            if (node.getAttribute("comment").startsWith(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Trouve la présence de tags sur l'image
     *
     * @param file Image à tester
     * @return True si il y des tags sinon false
     * @throws IOException
     */
    public boolean isTagged(File file) throws IOException {

        String extension = getFileExtension(file);

        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(extension);

        while (readers.hasNext()) {
            ImageReader reader = readers.next();

            try (ImageInputStream stream = ImageIO.createImageInputStream(file)) {

                reader.setInput(stream, true);

                IIOMetadata metadata = reader.getImageMetadata(0);

                return findTag(metadata, "heigViewer");

            }
        }

        return false;
    }

    /**
     * Récupère les tags d'une image
     *
     * @param path Chemin de l'image
     * @return Liste des tags présents sur l'image
     * @throws IOException
     */
    public ArrayList<String> getTag(String path) throws IOException {

        ArrayList<String> tags = new ArrayList<String>();

        File file = new File(path);

        String extension = getFileExtension(file);

        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(extension);

        if (readers.hasNext()) {
            ImageReader reader = readers.next();

            try (ImageInputStream stream = ImageIO.createImageInputStream(file)) {

                if (stream != null) {
                    reader.setInput(stream, true);

                    IIOMetadata metadata = reader.getImageMetadata(0);

                    tags = getTextEntry(metadata, "heigViewer");
                }

            }
        }

        return tags;
    }
}
