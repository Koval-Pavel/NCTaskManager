package ua.edu.sumdu.j2se.koval.tasks;

import ua.edu.sumdu.j2se.koval.tasks.controller.MainMenuController;
import ua.edu.sumdu.j2se.koval.tasks.view.MainMenuView;

public class Main {

	public static void main(String[] args) {
		new MainMenuController(new MainMenuView());
	}
}
