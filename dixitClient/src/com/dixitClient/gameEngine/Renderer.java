package com.dixitClient.gameEngine;

import com.dixitClient.gameEngine.gfx.Font;
import com.dixitClient.gameEngine.gfx.Image;
import com.dixitClient.gameEngine.gfx.ImageRequest;
import com.dixitClient.gameEngine.gfx.ImageTile;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Renderer
{
	private Font fontStandard = Font.STANDARD;
	private Font fontTitles = Font.TITLES;
	private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();

	private int pixelWidth, pixelHight;
	private int[] p;
	private int[] zb;

	private int zDepth = 0;
	private boolean processing = false;

	public Renderer(GameContainer gc)
	{
		pixelHight = gc.getHeight();
		pixelWidth = gc.getWidth();
		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zb= new int[p.length];
	}

	public void clear()
	{
		for(int i=0; i < p.length; i++)
		{
			p[i] = 0;
			zb[i] =0;
		}
	}

	public void process(){
		processing = true;
		imageRequest.sort((o1, o2) -> {
			if (o1.zDepth < o2.zDepth) return -1;
			if (o1.zDepth > o2.zDepth) return 1;
			return 0;
		});

		for (ImageRequest ir : imageRequest) {
			setzDepth(ir.zDepth);
			drawImage(ir.image, ir.offX, ir.offY);
		}
		processing = false;
		imageRequest.clear();
	}

	public void setPixel(int x, int y, int value)
	{
		int alpha = ((value >> 24) & 0xff);

		if((x<0 || x>= pixelWidth || y<0 || y>= pixelHight)|| alpha == 0)
		{
			return;
		}

		int index = x + y * pixelWidth;

		if( zb[index] > zDepth)
			return;

		zb[index] = zDepth;

		if(alpha == 255)
		{
			p[index] = value;
		}
		else {
			int pixelColor = p[index];

			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff))*(alpha / 255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff))*(alpha / 255f));
			int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value & 0xff))*(alpha / 255f));

			p[index] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
	}

	public void drawText(String text, int offX, int offY, int color, int fontFormat){
		Font font;

		if(fontFormat == 0){
			font = fontStandard;
		} else {
			font = fontTitles;
		}

		int offset= 0;

		for(int i=0; i < text.length(); i++){
			int unicode = text.codePointAt(i);

			for (int y = 0; y <font.getFontImage().getH(); y++){
				for (int x=0; x < font.getWidths()[unicode]; x++){
					if(font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y* font.getFontImage().getW()]== 0xffffffff){
						setPixel(x+offX+offset,y+offY,color);
					}
				}
			}

			offset += font.getWidths()[unicode];
		}
	}

	public void drawText(String text, int offX, int offY, int color, int fontFormat, boolean middle){
		Font font;

		if(fontFormat == 0){
			font = fontStandard;
		} else {
			font = fontTitles;
		}

		int lengths = 0;
		int offset= 0;

		for(int i=0; i < text.length(); i++) {
			int unicode = text.codePointAt(i);
			lengths += font.getWidths()[unicode];
		}

		for(int i=0; i < text.length(); i++){
			int unicode = text.codePointAt(i);

			for (int y = 0; y <font.getFontImage().getH(); y++){
				for (int x=0; x < font.getWidths()[unicode]; x++){
					if(font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y* font.getFontImage().getW()]== 0xffffffff){
						setPixel(x+offX-(int)(lengths/2.0f)+offset,y+offY,color);
					}
				}
			}

			offset += font.getWidths()[unicode];
		}
	}

	public void drawImage(Image image, int offX, int offY)
	{
		if(image.isAlpha() && !processing){
			imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
			return;
		}

		//Don't render code
		if(offX < -image.getW()) return;
		if(offY < -image.getH()) return;
		if(offX >= pixelWidth) return;
		if(offY >= pixelHight) return;

		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();

		//Clipping code
		if(offX < 0 ){newX -= offX;}
		if(offY < 0 ){newY -= offY;}
		if(newWidth + offX > pixelWidth){ newWidth -= (newWidth +offX - pixelWidth); }
		if(newHeight + offX > pixelHight){ newHeight -= (newHeight +offY - pixelHight); }

		for(int y = newY; y < newHeight; y++)
		{
			for(int x = newX; x < newWidth; x++)
			{
				setPixel(x+offX, y+offY, image.getP()[x+y* image.getW()]);
			}
		}
	}

	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY)
	{
		if(image.isAlpha() && !processing){
			imageRequest.add(new ImageRequest(image.getTileImage(tileX,tileY), zDepth, offX, offY));
			return;
		}

		//Don't render code
		if(offX < -image.getTileW()) return;
		if(offY < -image.getTileH()) return;
		if(offX >= pixelWidth) return;
		if(offY >= pixelHight) return;

		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileW();
		int newHeight = image.getTileH();

		//Clipping code
		if(offX < 0 ){newX -= offX;}
		if(offY < 0 ){newY -= offY;}
		if(newWidth + offX > pixelWidth){ newWidth -= (newWidth +offX - pixelWidth); }
		if(newHeight + offX > pixelHight){ newHeight -= (newHeight +offY - pixelHight); }

		for(int y = newY; y < newHeight; y++)
		{
			for(int x = newX; x < newWidth; x++)
			{
				setPixel(x+offX, y+offY, image.getP()[(x + tileX * image.getTileW()) +(y + tileY * image.getTileH()) * image.getW()]);
			}
		}
	}

	public void drawRect(int offX, int offY, int width, int height, int color){
		for(int y= 0; y<= height; y++){
			setPixel(offX,y+offY,color);
			setPixel(offX+width,y+offY,color);
		}
		for(int x= 0; x<= width; x++){
			setPixel(x+offX,offY,color);
			setPixel(x+offX,offY+height,color);
		}
	}
	public void drawFillRect(int offX, int offY, int width, int height, int color){
		//Don't render code
		if(offX < -width) return;
		if(offY < -height) return;
		if(offX >= pixelWidth) return;
		if(offY >= pixelHight) return;

		int newX = 0;
		int newY = 0;
		int newWidth = width;
		int newHeight = height;

		//Clipping code
		if(offX < 0 ){newX -= offX;}
		if(offY < 0 ){newY -= offY;}
		if(newWidth + offX > pixelWidth){ newWidth -= (newWidth +offX - pixelWidth); }
		if(newHeight + offX > pixelHight){ newHeight -= (newHeight +offY - pixelHight); }

		for(int y = newY; y < newHeight; y++)
		{
			for(int x = newX; x < newWidth; x++)
			{
				setPixel(x+offX,offY+y,color);
			}
		}

	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}
}
