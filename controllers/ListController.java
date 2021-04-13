package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.dao.BoardCategoryDAO;
import application.dao.BoardDAO;
import application.dao.GroupEnrollDAO;
import application.util.SessionData;
import application.vo.BoardCategoryVO;
import application.vo.BoardPagingVO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ListController implements Initializable {
	
	@FXML
	private TableView<TableRowDataModel> myTableView;
	@FXML
	private TableColumn<TableRowDataModel, Integer> numberColumn;
	@FXML
	private TableColumn<TableRowDataModel, String> titleColumn;
	@FXML
	private TableColumn<TableRowDataModel, String> writerColumn;
	@FXML
	private TableColumn<TableRowDataModel, Integer> viewColumn;
	@FXML
	private TableColumn<TableRowDataModel, Integer> idColumn;
	@FXML
	private ComboBox<String> selector;
	@FXML
	private AnchorPane listPane;
	@FXML
	private Pagination pagination;
	@FXML
	private Button newWriteBtn;
	@FXML
	private Button logoutBtn;
	@FXML
	private Button backBtn;
	@FXML
	private Button groupSignOutBtn;
	@FXML
	private Button addCategoryBtn;
	@FXML
	private TextField newCategoryName;
	@FXML
	private Label currentCategoryId;
	@FXML
	private Label searchData;
	@FXML
	private Button searchDataBtn;
	@FXML
	private TextField search;
	@FXML
	private ComboBox<String> selectType;
	
	
	private List<BoardCategoryVO> categoryList;
	private int member_id;
	private int group_id;
	private BoardCategoryDAO categoryDao = new BoardCategoryDAO();
	private BoardDAO boardDao = new BoardDAO();
	private GroupEnrollDAO enrollDao = new GroupEnrollDAO();
	private ObservableList<String> combo;
	private ObservableList<String> searchcombo;
	private List<BoardPagingVO> boardlist = new ArrayList<BoardPagingVO>();
	private String kind;
	
	// TableView�� Ŭ������ �� ó��
	/*
	 * @FXML void tableClick(MouseEvent event) { if
	 * (!myTableView.getSelectionModel().isEmpty()) { //������ ������ ���ϱ� //MemberVO mVo =
	 * myTableView.getSelectionModel().getSelectedItem(); TableRowDataModel mVo =
	 * myTableView.getSelectionModel().getSelectedItem();
	 * 
	 * numberColumn.setText(mVo.get()); contentColumn.setText(mVo.get());
	 * writerColumn.setText(mVo.get()); viewColumn.setText(mVo.get());
	 * 
	 * 
	 * 
	 * System.out.println("��ȣ : "+mVo.numberProperty());
	 * System.out.println("���� : "+mVo.contentProperty());
	 * System.out.println("�ۼ��� : "+mVo.writerProperty());
	 * System.out.println("��ȸ�� : "+mVo.viewProperty());
	 * 
	 * } }
	 */

	

	ObservableList<TableRowDataModel> myList;
	private final ChangeListener<Number> paginationChangeListener = (observable, oldValue, newValue) -> changePage(newValue);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		group_id = SessionData.getSessionData().get("group_id");
		member_id = SessionData.getSessionData().get("id");
		categoryList = categoryDao.get_board_category_list(group_id);
		
		newWriteBtn.setDisable(true);
		
		// �޺��ڽ� ����Ʈ
		combo = FXCollections.observableArrayList();
		for(int i=0;i<categoryList.size();i++) {
			combo.add(categoryList.get(i).getName());
		}
		selector.setItems(combo);
		
		searchcombo = FXCollections.observableArrayList();
		searchcombo.add("����");
		searchcombo.add("����");
		selectType.setItems(searchcombo);
		
		numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		writerColumn.setCellValueFactory(cellData -> cellData.getValue().writerProperty());
		viewColumn.setCellValueFactory(cellData -> cellData.getValue().viewProperty().asObject());
		
	 
		boardlist = new ArrayList<BoardPagingVO>();
		
		pagination.setDisable(true);
		pagination.setPageCount(0);
		pagination.setMaxPageIndicatorCount(5);
		pagination.currentPageIndexProperty().addListener(paginationChangeListener);
		/*
		 * pagination.setPageFactory(new Callback<Integer, Node>() {
		 * 
		 * @Override public Node call(Integer param) { return new
		 * Label(String.format("Current Page: %d", pagination.getCurrentPageIndex())); }
		 * });
		 */
		

	}

	public void choose() {
		for(int i=0;i<categoryList.size();i++) {
			if(categoryList.get(i).getName().equals(selector.getValue())) {
				currentCategoryId.setText(""+categoryList.get(i).getId());
				if(categoryList.get(i).getName().equals("��ü")) {
					newWriteBtn.setDisable(false);
					boardlist = boardDao.read_total_board_list_with_paging(group_id,1);
					pagination.setDisable(false);
					System.out.println(categoryDao.get_board_cnt_with_category(group_id, Integer.parseInt(currentCategoryId.getText())));
					pagination.setPageCount(categoryDao.get_board_cnt_with_category(group_id, Integer.parseInt(currentCategoryId.getText()))/5+1);
					myList = FXCollections.observableArrayList();
					//boardlist.forEach(board -> System.out.printf("%d %s %s %d %n",board.getBnum(),board.getTitle(),board.getWriter_name(),board.getView_cnt()));
					boardlist.forEach(boardvo -> myList.add(
							new TableRowDataModel(
									boardvo.getBnum(), 
									boardvo.getTitle(), 
									boardvo.getWriter_name(), 
									boardvo.getView_cnt(), boardvo.getId())));
					
					
					myTableView.setItems(myList);
				}
				else {
					newWriteBtn.setDisable(false);
					boardlist = boardDao.read_board_list_with_paging(group_id,Integer.parseInt(currentCategoryId.getText()),1);
					pagination.setDisable(false);
					System.out.println(categoryDao.get_board_cnt_with_category(group_id, Integer.parseInt(currentCategoryId.getText())));
					pagination.setPageCount(categoryDao.get_board_cnt_with_category(group_id, Integer.parseInt(currentCategoryId.getText()))/5+1);
					myList = FXCollections.observableArrayList();
					//boardlist.forEach(board -> System.out.printf("%d %s %s %d %n",board.getBnum(),board.getTitle(),board.getWriter_name(),board.getView_cnt()));
					boardlist.forEach(boardvo -> myList.add(
							new TableRowDataModel(
									boardvo.getBnum(), 
									boardvo.getTitle(), 
									boardvo.getWriter_name(), 
									boardvo.getView_cnt(), boardvo.getId())));
					
					
					myTableView.setItems(myList);
				}
			}
		}
		

//		newWriteBtn.setDisable(false);
//		boardlist = boardDao.read_board_list_with_paging(group_id,Integer.parseInt(currentCategoryId.getText()),1);
//		pagination.setDisable(false);
//		System.out.println(categoryDao.get_board_cnt_with_category(group_id, Integer.parseInt(currentCategoryId.getText())));
//		pagination.setPageCount(categoryDao.get_board_cnt_with_category(group_id, Integer.parseInt(currentCategoryId.getText()))/5+1);
//		myList = FXCollections.observableArrayList();
//		//boardlist.forEach(board -> System.out.printf("%d %s %s %d %n",board.getBnum(),board.getTitle(),board.getWriter_name(),board.getView_cnt()));
//		boardlist.forEach(boardvo -> myList.add(
//				new TableRowDataModel(
//						boardvo.getBnum(), 
//						boardvo.getTitle(), 
//						boardvo.getWriter_name(), 
//						boardvo.getView_cnt(), boardvo.getId())));
//		
//		
//		myTableView.setItems(myList);
		
		
	}
	
	
	private void changePage(Number newValue) {
		
		int nextPage = (int)newValue+1;
		boardlist = boardDao.read_board_list_with_paging(group_id, Integer.parseInt(currentCategoryId.getText()), nextPage);
		myList.clear();
		int size = boardlist.size();
		System.out.println("������ : "+size);
		boardlist.forEach(boardvo -> myList.add(
				new TableRowDataModel(
						boardvo.getBnum(), 
						boardvo.getTitle(), 
						boardvo.getWriter_name(), 
						boardvo.getView_cnt(), boardvo.getId())));
		myTableView.setItems(myList);

	}
	
	public void selectRow() {
		
		 //myTableView.setSelectionModel(null); //Focus �����ʰ� �ϰ� ���� ��
		 myTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableRowDataModel>() {
			  
		@Override public void changed(ObservableValue<? extends TableRowDataModel>
		 observable, TableRowDataModel oldValue, TableRowDataModel newValue) {
		 TableRowDataModel model = myTableView.getSelectionModel().getSelectedItem(); 
		 System.out.println("��ȣ : " + model.numberProperty().getValue());
		 System.out.println("���� : " + model.titleProperty().getValue());
		 System.out.println("�ۼ��� : " + model.writerProperty().getValue());
		 System.out.println("��ȸ�� : " + model.viewProperty().getValue());
		 System.out.println("���̵� : " + model.idProperty().getValue());
		 System.out.println("���õ� Item�� Index" +
		 myTableView.getSelectionModel().getSelectedIndex());
		 System.out.println("------------------");
		 try {
				detail(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 });
		 
	}
	
	public void detail(TableRowDataModel model) throws Exception {
		System.out.println("�󼼺��� ��û");
		int boardId = model.getId();
		System.out.println(boardId);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/detail.fxml"));
		AnchorPane pane = loader.load();
		
		
		//get the controller from the FXMLLoader
		DetailController controller = loader.getController();
		
		//set it in the controller
		controller.setCurrentCategoryId(currentCategoryId.getText());
		controller.setBoardId(""+boardId);
		
        
		
		Scene scene = new Scene(pane);
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) newWriteBtn.getScene().getWindow();
		
		try{
			primaryStage.setScene(scene);
		} catch(Exception e) {
			System.out.println("���� ĳġ");
		}
		primaryStage.show();
	}
		 
	
	public void newWrite() throws Exception {
		System.out.println("���ۼ� �õ�");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/newWrite.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		NewWriteController controller = loader.getController();
		
		//set it in the controller
		//ī�װ���
		controller.setCurrentCategoryId(currentCategoryId.getText());
		System.out.println(currentCategoryId.getText());
		Scene scene = new Scene(pane);
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) newWriteBtn.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void logout() throws Exception {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�α׾ƿ� ��û");
		SessionData.getSessionData().clear();
		
		alert.setAlertType(AlertType.CONFIRMATION);;
		alert.setContentText("�α׾ƿ� �Ͻðڽ��ϱ�?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText("�α׾ƿ�");
			alert.setContentText("�α׾ƿ� �Ϸ�");
		    alert.showAndWait();
		    
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/login.fxml"));
			AnchorPane pane = loader.load();
			
			//get the controller from the FXMLLoader
			LoginController controller = loader.getController();
			
			//set it in the controller
			
			Scene scene = new Scene(pane);
			
			//creatAccountButton�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
			Stage primaryStage = (Stage) logoutBtn.getScene().getWindow();
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}
	
	public void backToGroupList() throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/groupList.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		GroupListController controller = loader.getController();
		
		//set it in the controller
		
        
		Scene scene = new Scene(pane);
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) backBtn.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void groupSignOut() throws Exception {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�׷�Ż�� ��û");
		
		
		
		alert.setContentText("�׷쿡�� Ż���Ͻðڽ��ϱ�?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			boolean flag = enrollDao.signout_group(member_id, group_id);
			if(flag==false) {
				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText("�׷����� Ż���� �� �����ϴ�.");
				alert.showAndWait();
				return;
			}
			else {
				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("�׷�Ż��");
				alert.setContentText("�׷�Ż�� �Ϸ�");
			    alert.showAndWait();
			    backToGroupList();
			}
		}
	}
	
	public void addCategory() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("ī�װ� ���� ��û");
		
		if(newCategoryName.getText().isEmpty()) {
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("ī�װ����� �Է��ϼ���.");
			alert.showAndWait();
			return;
		}
		
		
		alert.setContentText(newCategoryName.getText()+" ī�װ��� �����Ͻðڽ��ϱ�?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			boolean flag = categoryDao.make_board_category(group_id, newCategoryName.getText());
			if(flag==false) {
				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText("�̹� �����ϴ� ī�װ��Դϴ�.");
				alert.showAndWait();
				return;
			}
			else {
				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("ī�װ� ����");
				alert.setContentText("ī�װ� ���� �Ϸ�");
			    alert.showAndWait();
			    categoryList = categoryDao.get_board_category_list(group_id);
				
				
				// �޺��ڽ� ����Ʈ
				combo = FXCollections.observableArrayList();
				for(int i=0;i<categoryList.size();i++) {
					combo.add(categoryList.get(i).getName());
				}
				selector.setItems(combo);
			}
		}
		
		
	}
	public void select() {
		kind=selectType.getValue();
	}
	public void searchData() {
//		SessionData.getSessionData().put("searchKind", "����")
		for(int i = 0;i <categoryList.size();i++) {
			Integer.parseInt(currentCategoryId.getText());
			if(Integer.parseInt(currentCategoryId.getText()) == categoryList.get(i).getId()) {
				if(categoryList.get(i).getName().equals("��ü")) {
					boardlist = boardDao.read_total_board_list_with_searching_and_paging(group_id, 1, kind, search.getText());
					myList = FXCollections.observableArrayList();
					boardlist.forEach(board -> System.out.println("��ü "+ board.getTitle()));
					boardlist.forEach(boardvo -> myList.add(
							new TableRowDataModel(
									boardvo.getBnum(), 
									boardvo.getTitle(), 
									boardvo.getWriter_name(), 
									boardvo.getView_cnt(), boardvo.getId())));
				}
				else {
					boardlist = boardDao.read_board_list_with_searching_and_paging(group_id, Integer.parseInt(currentCategoryId.getText()), 1, kind, search.getText());
					myList = FXCollections.observableArrayList();
					boardlist.forEach(board -> System.out.println(board.getTitle()));
					boardlist.forEach(boardvo -> myList.add(
							new TableRowDataModel(
									boardvo.getBnum(), 
									boardvo.getTitle(), 
									boardvo.getWriter_name(), 
									boardvo.getView_cnt(), boardvo.getId())));
				}
				break;
			}
			
		}

		
	//	boardlist.forEach(board -> System.out.printf("%d %s %s %d %d %n", board.getBnum(),board.getTitle(), board.getWriter_name(), board.getView_cnt(), board.getId()));
		
		System.out.println("���̸���Ʈ:" + myList.size());
		myTableView.setItems(myList);
	}

}