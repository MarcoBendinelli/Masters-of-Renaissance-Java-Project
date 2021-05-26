package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.cli.AnsiEnum;

import java.util.List;

public abstract class Printable {

    private int width = -1;

    public void setWidth(List<String> prePrintable) {
        this.width = prePrintable.stream().map(AnsiEnum::getStringLengthWithoutANSI).max(Integer::compareTo).orElse(-1);
    }

    public int getWidth() {
        if(this.width == -1)
            getPrintable();
        return this.width;
    }

    public String getEmptySpace() {
        StringBuilder emptySpace = new StringBuilder();
        for(int i = 0; i < getWidth(); i++)
            emptySpace.append(' ');
        return emptySpace.toString();
    }

    public List<String> getPrintable() {
        return null;
    }

    public String fillWithEmptySpace(String toFill) {
        int toFillLength = AnsiEnum.getStringLengthWithoutANSI(toFill);
        if (toFillLength < this.width) {
            StringBuilder filler = new StringBuilder(toFill);
            for (int i = 0; i < (this.width - toFillLength); i++)
                filler.append(' ');
            return filler.toString();
        }
        return toFill;
    }
}