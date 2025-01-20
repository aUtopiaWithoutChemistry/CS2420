package assign01;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Represents a grayscale (black and white) image as a 2D array of "pixel"
 * brightness values: 255 is "white", 127 is "gray", and 0 is "black" with
 * intermediate values in between.
 * 
 * @author CS 2420 course staff and Zifan Zuo
 * @version 2025-01-13
 */
public class GrayscaleImage {

	private double[][] imageData; // array of pixel brightness values

	/**
	 * Initializes an image from a 2D array of doubles. This constructor creates a
	 * copy of the input array.
	 * 
	 * @param data initial pixel values
	 * @throws IllegalArgumentException if the input array is empty or "jagged"
	 *                                  meaning not all rows are the same length
	 */
	public GrayscaleImage(double[][] data) {
		if(data.length == 0 || data[0].length == 0) {
			throw new IllegalArgumentException("Image is empty");
		}

		imageData = new double[data.length][data[0].length];
		for(int row = 0; row < imageData.length; row++) {
			if(data[row].length != imageData[row].length) {
				throw new IllegalArgumentException("All rows must have the same length");
			}
			for(int col = 0; col < imageData[row].length; col++) {
				imageData[row][col] = data[row][col];
			}
		}
	}

	/**
	 * Fetches an image from the specified URL and converts it to grayscale. Uses
	 * the AWT Graphics2D class to do the conversion, so it may add an item to your
	 * dock/menu bar as if you are loading a GUI program.
	 * 
	 * @param url from which to download the image
	 * @throws IOException if the image cannot be downloaded for some reason
	 */
	public GrayscaleImage(URL url) throws IOException {
		BufferedImage inputImage = ImageIO.read(url);
		// Converts input image to grayscale based on information from
		// https://stackoverflow.com/questions/6881578/how-to-convert-between-color-models
		BufferedImage grayImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
				BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2d = grayImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, null);
		g2d.dispose();
		imageData = new double[grayImage.getHeight()][grayImage.getWidth()];

		// Raster is basically a width x height x 1 3D array
		WritableRaster grayRaster = grayImage.getRaster();
		for(int row = 0; row < imageData.length; row++) {
			for(int col = 0; col < imageData[0].length; col++) {
				// getSample parameters are x (our column) and y (our row); i.e., "backwards"
				imageData[row][col] = grayRaster.getSampleDouble(col, row, 0);
			}
		}
	}

	/**
	 * Saves the image as a PNG file.
	 * 
	 * @param filename of the created image file
	 * @throws IOException if the file cannot be written
	 */
	public void savePNG(File filename) throws IOException {
		BufferedImage outputImage = new BufferedImage(imageData[0].length, imageData.length,
				BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = outputImage.getRaster();
		for(int row = 0; row < imageData.length; row++) {
			for(int col = 0; col < imageData[0].length; col++) {
				raster.setSample(col, row, 0, imageData[row][col]);
			}
		}
		ImageIO.write(outputImage, "png", filename);
	}

	/**
	 * This instance method returns the pixel brightness value at the specified coordinates.
	 * If x or y are not within the image width or height, an IllegalArgumentException is thrown.
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return brightness value of given pixel
	 */
	public double getPixel(int y, int x) {
		int status = isCoordinationValid(x, y);
		if(status == 1) {
			throw new IllegalArgumentException("invalid x value");
		} else if (status == 2) {
			throw new IllegalArgumentException("invalid y value");
		} else {
			return this.imageData[x][y];
		}
	}

	/**
	 * This instance method sets the pixel brightness value at the specified coordinates.
	 * If x or y are not within the image width or height, or if brightnessValue is out
	 * of range, an IllegalArgumentException is thrown.
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param brightnessValue new brightness value for this coordinate
	 */
	public void setPixel(int y, int x, double brightnessValue) {
		int status = isCoordinationValid(x, y);
		if(status == 1) {
			throw new IllegalArgumentException("invalid x value");
		} else if (status == 2) {
			throw new IllegalArgumentException("invalid y value");
		} else if (brightnessValue > 255.0) {
			throw new IllegalArgumentException("brightnessValue out of range");
		} else {
			imageData[x][y] = brightnessValue;
		}
	}

	/**
	 * This instance method returns true if other is a GrayscaleImage, has the same size,
	 * and each corresponding pixel has exactly the same value as this. It returns false otherwise.
	 *
	 * @param other another grayscale image
	 * @return true if other image is exactly the same value as this
	 * @throws IllegalArgumentException if the other object is empty
	 */
	public boolean equals(Object other) {
		if (other == null) {
			throw new IllegalArgumentException("image does not exist");
		}
		boolean status = false;
		GrayscaleImage otherImage = null;
		if (other instanceof GrayscaleImage) {
			otherImage = (GrayscaleImage) other;
			if (this.imageData.length == otherImage.imageData.length) {
				status = true;
			}
		}
		if (status) {
			for (int i = 0; i < this.imageData.length; i++) {
				for (int j = 0; j < this.imageData[i].length; j++) {
					if (this.imageData[i][j] != otherImage.imageData[i][j]) {
						return false;
					}
				}
			}
		}
		return status;
	}

	/**
	 * This instance method computes and returns the average of all brightness values in the image.
	 *
	 * @return the average brightness of all the pixels in ths image
	 */
	public double averageBrightness() {
		double total = 0;
		int count = 0;
        for (double[] imageDatum : this.imageData) {
            for (double v : imageDatum) {
                total += v;
                count++;
            }
        }
		return total / count;
	}

	/**
	 * This instance method creates and returns a new, similar GrayscaleImage where the average
	 * brightness is 127. Due to rounding errors, the new average brightness
	 * might not be 127 exactly, but it will be very close. The original image will not be modified.
	 *
	 * @return a new grayscale image with normalized data.
	 */
	public GrayscaleImage normalized() {
		double average = this.averageBrightness();
		double targetAve = 127.0;
		double factor = targetAve / average;
		double[][] newData = new double[imageData.length][imageData[0].length];
		for (int i = 0; i < imageData.length; i++) {
			for (int j = 0; j < imageData[0].length; j++) {
				newData[i][j] = imageData[i][j] * factor;
			}
		}
        return new GrayscaleImage(newData);
	}

	/**
	 * This instance method modifies the GrayscaleImage by inverting the brightness values.
	 * This means the brightness value of each pixel becomes (255 - value).
	 */
	public void invert() {
		for (int i = 0; i < imageData.length; i++) {
			for (int j = 0; j < imageData[0].length; j++) {
				imageData[i][j] = 255 - imageData[i][j];
			}
		}
	}

	/**
	 * This static method updates the given GrayscaleImage such that it is "mirrored" across
	 * the y-axis. In other words, each row from the image will be reversed. It will
	 * update each pixel of image.
	 *
	 * @param image image to be mirrored
	 * @throws IllegalArgumentException if given image is null.
	 */
	public static void mirrored(GrayscaleImage image) {
		if (image == null) {
			throw new IllegalArgumentException("image cannot be read");
		}
		int xSize = image.imageData.length;
		int ySize = image.imageData[0].length;
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < (ySize / 2); j++) {
				double temp = image.imageData[i][j];
				image.imageData[i][j] = image.imageData[i][ySize - j - 1];
				image.imageData[i][ySize - j - 1] = temp;
			}
		}
	}

	/**
	 * a private helper method to test if a given coordinate is a valid input.
	 * if it's valid return 0, if x is not valid return 1, if y is not valid return 2.
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return status num, 0 for valid, 1 for invalid x, 2 for invalid y
	 */
	private int isCoordinationValid(int x, int y) {
		if (x >= this.imageData.length || x < 0) {
			return 1;
		} else if (y >= this.imageData[0].length || y < 0) {
			return 2;
		}
		return 0;
	}
}