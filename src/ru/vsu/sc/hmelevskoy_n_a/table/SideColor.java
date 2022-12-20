package ru.vsu.sc.hmelevskoy_n_a.table;

public enum SideColor {
 GOOSE , FOX;
 
 public SideColor swapColor() {
	 if(this ==SideColor.GOOSE) return SideColor.FOX;
	 return  SideColor.GOOSE;
 }
 public String getBetterString() {
	 if(this ==SideColor.GOOSE) return "GOOSE";
	 return  "FOX";

 }
 
}
