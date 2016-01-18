package com.ELang;

/**
 * Created by Juliang on 1/17/16.
 */
public class TokenStream {
    private String source;
    private int index;
    public TokenStream(String sourceCode){
        this.source = sourceCode;
        this.index = 0;
    }

    private boolean hasSource(){
        return this.index < this.source.length();
    }
    /**
     * consume one character from the string if input matches
     * @param c character
     * @return true if input matches
     */
    private boolean getChar(char c){
        if (this.source.charAt(this.index) == c){
            ++this.index;
            return true;
        }else{
            return false;
        }
    }
    private char getChar(){
        return this.source.charAt(this.index++);
    }
    private void backSpace(){
        --this.index;
    }
    /**
     * consume a word from the input string if input matches
     * @param s a word
     * @return true if input matches
     */
    public boolean getWord(String s){
        for (int i = 0; i < s.length(); ++i){
            if (this.getChar(s.charAt(i)))
                continue;
            else{
                this.index -= i;
                return false;
            }
        }
        return true;
    }
    public Number getNumber(){
        String result = "";
        int count = 0;
        for (; hasSource(); ++count){
            char c = this.getChar();
            if (count == 0 && c == '-')
                result += c;
            else if (c == '.' && result.indexOf('.') == -1)
                result += c;
            else if (Character.isDigit(c)){
                result += c;
            }
            else {
                this.backSpace();
                break;
            }
        }
        try {
            if (result.length() != 0) {
                if (result.indexOf(".") != -1)
                    return Double.parseDouble(result);
                else
                    return Integer.parseInt(result);
            } else
                return null;
        }catch (NumberFormatException e){
            this.index -= count;
            return null;
        }
    }

    public String getIdentifier(){
        String result = "";
        int count = 0;
        for (; hasSource(); ++count){
            char c = this.getChar();
            if (count == 0 && Character.isLetter(c))
                result += c;
            else if (count != 0 && Character.isLetterOrDigit(c))
                result += c;
            else {
                this.backSpace();
                break;
            }
        }
        return result.length() == 0 ? null : result;
    }
    public String getString(){
        int index = getCurrentState();
        StringBuilder sb = new StringBuilder();
        try {
            if (getChar('\n')) {
                while (true) {
                    char c = getChar();
                    if (c != '\n')
                        sb.append(c);
                    else {
                        return sb.toString();
                    }
                }
            }
        }catch(StringIndexOutOfBoundsException e){
            // fail
        }finally {
            restoreState(index);
            return null;
        }
    }

    public int getCurrentState(){
        return this.index;
    }
    public void restoreState(int i){
        this.index = i;
    }
    /**
     * return the remaining source code
     * @return string
     */
    @Override
    public String toString(){
        return this.source.substring(this.index);
    }
}
