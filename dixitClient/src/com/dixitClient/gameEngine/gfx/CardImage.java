package com.dixitClient.gameEngine.gfx;

public class CardImage extends Image{
    int id;

    public CardImage(int id, int width, int height) {
        super("/"+id+".png",width,height);
        this.id= id;
    }

}
