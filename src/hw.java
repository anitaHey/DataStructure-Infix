import java.io.IOException;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class hw extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Scene scene = new Scene(new StackPane());
		Locale.setDefault(Locale.ENGLISH);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("style.fxml"));
		scene.setRoot(loader.load());
		loader.setController(this);
		primaryStage.setTitle("中序轉換");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	Button trans;
	@FXML
	RadioButton post, pre;
	@FXML
	Label ans, arr0, arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, pre0, pre1, pre2, pre3, pre4,
			pre5, pre6, pre7, pre8, pre9, pre10, pre11, post0, post1, post2, post3, post4, post5, post6, post7, post8,
			post9, post10, post11;
	@FXML
	VBox view, pre_view, post_view;
	@FXML
	TextField input;
	Label[] arr;
	Label[] prearr;
	Label[] postarr;
	char[] pre_ans;
	char[] post_ans;
	ToggleGroup choose;
	@FXML
	public void initialize() {
		arr = new Label[12];
		arr[0] = arr0;
		arr[1] = arr1;
		arr[2] = arr2;
		arr[3] = arr3;
		arr[4] = arr4;
		arr[5] = arr5;
		arr[6] = arr6;
		arr[7] = arr7;
		arr[8] = arr8;
		arr[9] = arr9;
		arr[10] = arr10;
		arr[11] = arr11;
		prearr = new Label[12];
		prearr[0] = pre0;
		prearr[1] = pre1;
		prearr[2] = pre2;
		prearr[3] = pre3;
		prearr[4] = pre4;
		prearr[5] = pre5;
		prearr[6] = pre6;
		prearr[7] = pre7;
		prearr[8] = pre8;
		prearr[9] = pre9;
		prearr[10] = pre10;
		prearr[11] = pre11;
		postarr = new Label[12];
		postarr[0] = post0;
		postarr[1] = post1;
		postarr[2] = post2;
		postarr[3] = post3;
		postarr[4] = post4;
		postarr[5] = post5;
		postarr[6] = post6;
		postarr[7] = post7;
		postarr[8] = post8;
		postarr[9] = post9;
		postarr[10] = post10;
		postarr[11] = post11;
		choose = new ToggleGroup();
		post.setToggleGroup(choose);
		pre.setToggleGroup(choose);
		trans.setOnAction((event) -> {
			transform();
		});
	}

	public void transform() {
		pre_view.getChildren().clear();
		post_view.getChildren().clear();
		for(int a=0;a<12;a++){
			arr[a].setText("");
			postarr[a].setText("");
			prearr[a].setText("");
		}
		view.getChildren().clear();
		String inputString = input.getText();
		if (inputString.length() < 3 || inputString.length() > 12) {
			input.setStyle("-fx-font-size: 20; -fx-border-color: #D25565; -fx-border-width: 3;");
			input.setText("");
			input.setPromptText("輸入錯誤!請輸入3~12字以內的算式");
		} else {
			input.setStyle("-fx-font-size: 20; -fx-border-color: #716e77; -fx-border-width: 3;");
			input.setPromptText("請輸入3~12字以內的算式");
			char[] inputarr = new char[inputString.length()];
			for (int a = 0; a < inputString.length(); a++) {
				inputarr[a] = inputString.charAt(a);
				arr[a].setText(String.valueOf(inputString.charAt(a)));
			}
			pre_change(inputarr);
			post_change(inputarr);
			RadioButton selectedRadioButton = (RadioButton) choose.getSelectedToggle();
			String toogleGroupValue = selectedRadioButton.getText();
			if(selectedRadioButton.getText()=="前序")
				pre_value(pre_ans);
			else
				pre_value(post_ans);
		}
	}

	public void pre_change(char[] inputarr) {
		char[] stack = new char[inputarr.length];
		pre_ans = new char[inputarr.length];
		String stack_str="",ans_str="";
		int top = 0, num =  0;
		for (int a = inputarr.length - 1; a >= 0; a--) {
			switch (inputarr[a]) {
			case '(':
				while (stack[top] != ')') {
					Label temLabel = new Label("Stack Pop "+stack[top]);
					temLabel.setStyle("-fx-font-size:20");
					pre_view.getChildren().add(temLabel);
					
					pre_ans[num] = stack[top--];
					
					ans_str="--輸出:";
					for(int b = inputarr.length-1;b>=0;b--){
						if(pre_ans[b]!=0)
							ans_str=ans_str+" "+pre_ans[b];
					}
					temLabel = new Label(ans_str);
					temLabel.setStyle("-fx-font-size:20");
					pre_view.getChildren().add(temLabel);
					num++;
				}
				top--;
				break;
			case '+':
			case '-':
			case '*':
			case '/':
				while (priority(stack[top]) >= priority(inputarr[a])) {
					Label temLabel = new Label("Stack Pop "+stack[top]);
					temLabel.setStyle("-fx-font-size:20");
					pre_view.getChildren().add(temLabel);
					
					pre_ans[num] = stack[top--];
					
					ans_str="--輸出:";
					for(int b = inputarr.length-1;b>=0;b--){
						if(pre_ans[b]!=0)
							ans_str=ans_str+" "+pre_ans[b];
					}
					temLabel = new Label(ans_str);
					temLabel.setStyle("-fx-font-size:20");
					pre_view.getChildren().add(temLabel);
					num++;
				}
				Label temLabel = new Label("Stack Push "+inputarr[a]);
				temLabel.setStyle("-fx-font-size:20");
				pre_view.getChildren().add(temLabel);
				
				stack[++top] = inputarr[a];
				
				stack_str="--stack:";
				for(int b = 0;b<inputarr.length;b++)
					if(stack[b]!=0)
						stack_str=stack_str+" "+stack[b];
				
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				pre_view.getChildren().add(temLabel);
				break;
			case ')':
				temLabel = new Label("Stack Push "+inputarr[a]);
				temLabel.setStyle("-fx-font-size:20");
				pre_view.getChildren().add(temLabel);
				
				stack[++top] = inputarr[a];
				
				stack_str="--stack:";
				for(int b = 0;b<inputarr.length;b++)
					if(stack[b]!=0)
						stack_str=stack_str+" "+stack[b];
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				pre_view.getChildren().add(temLabel);
				break;
			default: 
				pre_ans[num] = inputarr[a];
				ans_str="--輸出:";
				for(int b = inputarr.length-1;b>=0;b--)
					if(pre_ans[b]!=0)
						ans_str=ans_str+" "+pre_ans[b];
				temLabel = new Label(ans_str);
				temLabel.setStyle("-fx-font-size:20");
				pre_view.getChildren().add(temLabel);
				num++;
			}

		}
		while (top > 0) {
			Label temLabel = new Label("Stack Pop "+stack[top]);
			temLabel.setStyle("-fx-font-size:20");
			pre_view.getChildren().add(temLabel);
			
			pre_ans[num] = stack[top--];
			ans_str="--輸出:";
			for(int b = inputarr.length-1;b>=0;b--)
				if(pre_ans[b]!=0)
					ans_str=ans_str+" "+pre_ans[b];
			temLabel = new Label(ans_str);
			temLabel.setStyle("-fx-font-size:20");
			pre_view.getChildren().add(temLabel);
			num++;
		}
		int tem=0;
		for(int a = inputarr.length-1;a>=0;a--){
			if(pre_ans[a]!=0)
			prearr[tem++].setText(String.valueOf(pre_ans[a]));
		}
	}
	public void post_change(char[] inputarr) {
		char[] stack = new char[inputarr.length];
		post_ans = new char[inputarr.length];
		String stack_str="",ans_str="";
		int top = 0, num =  0;
		for (int a = 0; a <inputarr.length ; a++) {
			switch (inputarr[a]) {
			case '(':
				Label temLabel = new Label("Stack Push "+inputarr[a]);
				temLabel.setStyle("-fx-font-size:20");
				post_view.getChildren().add(temLabel);
				
				stack[++top] = inputarr[a];
				
				stack_str="--stack:";
				for(int b = 0;b<inputarr.length;b++)
					if(stack[b]!=0)
						stack_str=stack_str+" "+stack[b];
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				post_view.getChildren().add(temLabel);
				break;
			case '+':
			case '-':
			case '*':
			case '/':
				while (priority(stack[top]) >= priority(inputarr[a])) {
					temLabel = new Label("Stack Pop "+stack[top]);
					temLabel.setStyle("-fx-font-size:20");
					post_view.getChildren().add(temLabel);
					
					post_ans[num] = stack[top--];
					
					ans_str="--輸出:";
					for(int b = 0;b<inputarr.length;b++){
						if(post_ans[b]!=0)
							ans_str=ans_str+" "+post_ans[b];
					}
					temLabel = new Label(ans_str);
					temLabel.setStyle("-fx-font-size:20");
					post_view.getChildren().add(temLabel);
					num++;
				}
				temLabel = new Label("Stack Push "+inputarr[a]);
				temLabel.setStyle("-fx-font-size:20");
				post_view.getChildren().add(temLabel);
				
				stack[++top] = inputarr[a];
				
				stack_str="--stack:";
				for(int b = 0;b<inputarr.length;b++)
					if(stack[b]!=0)
						stack_str=stack_str+" "+stack[b];
				
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				post_view.getChildren().add(temLabel);
				break;
			case ')':
				while (stack[top] != '(') { 
					temLabel = new Label("Stack Pop "+stack[top]);
					temLabel.setStyle("-fx-font-size:20");
					post_view.getChildren().add(temLabel);
					
					post_ans[num] = stack[top--];
					
					ans_str="--輸出:";
					for(int b = 0;b<inputarr.length;b++){
						if(post_ans[b]!=0)
							ans_str=ans_str+" "+post_ans[b];
					}
					temLabel = new Label(ans_str);
					temLabel.setStyle("-fx-font-size:20");
					post_view.getChildren().add(temLabel);
					num++;
				}
				top--;
				break;
			default: 
				post_ans[num] = inputarr[a];
				ans_str="--輸出:";
				for(int b = 0;b<inputarr.length;b++)
					if(post_ans[b]!=0)
						ans_str=ans_str+" "+post_ans[b];
				temLabel = new Label(ans_str);
				temLabel.setStyle("-fx-font-size:20");
				post_view.getChildren().add(temLabel);
				num++;
			}

		}
		while (top > 0) {
			Label temLabel = new Label("Stack Pop "+stack[top]);
			temLabel.setStyle("-fx-font-size:20");
			post_view.getChildren().add(temLabel);
			
			post_ans[num] = stack[top--];
			ans_str="--輸出:";
			for(int b = 0;b<inputarr.length;b++)
				if(post_ans[b]!=0)
					ans_str=ans_str+" "+post_ans[b];
			temLabel = new Label(ans_str);
			temLabel.setStyle("-fx-font-size:20");
			post_view.getChildren().add(temLabel);
			num++;
		}
		for(int a = 0;a<inputarr.length;a++){
			if(post_ans[a]!=0)
			postarr[a].setText(String.valueOf(post_ans[a]));
		}
	}
	public void pre_value(char[] array){
		String[] stack =  new String[array.length];
		System.out.println(array);
		String stack_str="",ans_str="";
		int num=0;
		for(int a = 0;a<array.length;a++){
			switch(array[a]){
			case '+':
				stack_str="Scan:+";
				Label temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				num--;
				int num1 = Integer.valueOf(stack[num--]);
				stack_str="pop:"+num1;
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				int num2 = Integer.valueOf(stack[num]);
				stack_str="pop:"+num2;
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				stack[num++]=String.valueOf(num1+num2);
				stack_str="push:"+(num1+num2);
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				stack[num]=null;
				stack_str="--stack:";
				for(int b = 0;b<stack.length;b++)
					if(stack[b]!=null)
						stack_str=stack_str+" "+stack[b];
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				break;
			case '-':
				stack_str="Scan:-";
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				num--;
				 num1 = Integer.valueOf(stack[num--]);
				 stack_str="pop:"+num1;
					temLabel = new Label(stack_str);
					temLabel.setStyle("-fx-font-size:20");
					view.getChildren().add(temLabel);
				 num2 = Integer.valueOf(stack[num]);
				 stack_str="pop:"+num2;
					temLabel = new Label(stack_str);
					temLabel.setStyle("-fx-font-size:20");
					view.getChildren().add(temLabel);
				stack[num++]=String.valueOf(num1-num2);
				stack_str="push:"+(num1-num2);
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				stack[num]=null;
				stack_str="--stack:";
				for(int b = 0;b<stack.length;b++)
					if(stack[b]!=null)
						stack_str=stack_str+" "+stack[b];
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				break;
			case '*':
				stack_str="Scan:*";
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				num--;
				 num1 = Integer.valueOf(stack[num--]);
				 stack_str="pop:"+num1;
					temLabel = new Label(stack_str);
					temLabel.setStyle("-fx-font-size:20");
					view.getChildren().add(temLabel);
				 num2 = Integer.valueOf(stack[num]);
				 stack_str="pop:"+num2;
					temLabel = new Label(stack_str);
					temLabel.setStyle("-fx-font-size:20");
					view.getChildren().add(temLabel);
				stack[num++]=String.valueOf(num2*num1);
				stack_str="push:"+(num1*num2);
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				stack[num]=null;
				stack_str="--stack:";
				for(int b = 0;b<stack.length;b++)
					if(stack[b]!=null)
						stack_str=stack_str+" "+stack[b];
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				break;
			case '/':
				stack_str="Scan:/";
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				num--;
				 num1 = Integer.valueOf(stack[num--]);
				 stack_str="pop:"+num1;
					temLabel = new Label(stack_str);
					temLabel.setStyle("-fx-font-size:20");
					view.getChildren().add(temLabel);
				 num2 = Integer.valueOf(stack[num]);
				 stack_str="pop:"+num2;
					temLabel = new Label(stack_str);
					temLabel.setStyle("-fx-font-size:20");
					view.getChildren().add(temLabel);
				stack[num++]=String.valueOf(num1/num2);
				stack_str="push:"+(num1/num2);
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				stack[num]=null;
				stack_str="--stack:";
				for(int b = 0;b<stack.length;b++)
					if(stack[b]!=null)
						stack_str=stack_str+" "+stack[b];
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				break;
			default:
				stack_str="push:"+(array[a]);
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				stack[num]= String.valueOf(array[a]);
				stack_str="--stack:";
				for(int b = 0;b<stack.length;b++)
					if(stack[b]!=null)
						stack_str=stack_str+" "+stack[b];
				temLabel = new Label(stack_str);
				temLabel.setStyle("-fx-font-size:20");
				view.getChildren().add(temLabel);
				num++;
			}		
			
			}
		ans_str="answer : "+stack[0];
		Label temLabel = new Label(ans_str);
		temLabel.setStyle("-fx-font-size:20");
		view.getChildren().add(temLabel);
		ans.setText(stack[0]);
		}
		
	public int priority(char op) {
		switch (op) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		default:
			return 0;
		}
	}
}