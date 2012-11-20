/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.questions;

/**
 *
 * @author Laetitia
 */
public class Answer {
	private boolean valid;
	private String str;

	public Answer(String str, boolean valid) {
		this.valid = valid;
		this.str = str;
	}
	public boolean isValid() {
		return this.valid;
	}
	public String getStr() {
		return this.str;
	}
}