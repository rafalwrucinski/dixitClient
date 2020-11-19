package com.dixitClient.gameEngine.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image
{
	private int[] p;
	private int w;
	private int h;
	private boolean alpha = false;

	public Image(String path)
	{
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}

		w = image.getWidth();
		h = image.getHeight();
		p = image.getRGB(0, 0, w, h, null, 0, w);
		
		image.flush();
	}

	public Image(int[] p, int w, int h){
		this.p =p;
		this.w = w;
		this.h = h;
	}

	public boolean isAlpha() {
		return alpha;
	}

	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}

	public int[] getP() {
		return p;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}


}
