package controllers;

import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.LinkedList;  
import java.util.List;  
  
public class mAn {
	private List<String> listResult;
	
	public mAn(){
		listResult = new ArrayList<String>();
	}

	public static void main(String[] args) {  
        String[] n ={"1","2","3","4"};  
        mAn m=new mAn();  
        List lst = Arrays.asList(n);  
        m.take("",2,lst);
        for(int i=0; i< m.listResult.size();i++)
        	System.out.println(m.listResult.get(i));
    }  
  
    public void take(String s, int total, List lst) {  
        for (int i = 0; i < lst.size(); i++) {  
            //System.out.println("i="+i);  
            List templst=new LinkedList(lst);  
            String n =  (String) templst.remove(i);// 取出来的数字
            String str = "";
            if(!s.equals(""))
            	str = s + ";" + n;  
            else
            	str = s + n;  
            if (total == 1) {  
                listResult.add(str);//以最极端 n个里面只取一个，直接把取出来的结果输出即可  
              
            } else {  
                int temp=total-1;//在同一层中total总量不能减,不能再原有变量的基础上  
                take(str, temp, templst);// 这里的temp以及templst都是全新的变量和集合  
            }  
        }  
          
    }  
  
}  