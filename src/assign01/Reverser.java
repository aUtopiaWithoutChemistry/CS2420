package assign01;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * This is a program that uses your GrayScaleImage class. It will download an
 * image from the Internet, edit it, and save a PNG file as 'outputImage.png'.
 * Be sure to include the 'https://' part when entering the image URL.
 * 
 * @author CS 2420 course staff
 * @version January 9, 2025
 */
public class Reverser {
	public static void main(String[] args) {
		String url;
		// The URL could either be provided as a command-line argument or
		// it will be prompted for in the console.
		if(args.length > 0)
			url = args[0];
		else {
			System.out.println("Enter input image URL");
			Scanner scanner = new Scanner(System.in);
			url = scanner.nextLine();
			scanner.close();
		}

		try {
			GrayscaleImage gi = new GrayscaleImage(new URI(url).toURL());
			gi.invert();
			gi = gi.normalized();
			GrayscaleImage.mirrored(gi);
			gi.savePNG(new File("outputImage.png"));
		}
		catch (URISyntaxException ex) {
			System.out.println("URL " + url + " is not valid: " + ex.getMessage());
		}
		catch (IOException ex) {
			System.out.println("Failed to download or save file: " + ex.getMessage());
		}
	}
}