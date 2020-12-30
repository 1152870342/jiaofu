import java.io.InputStream;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        boolean bo=true;
        while(bo) {
            Scanner sc = new Scanner(System.in);

            System.out.println("请输入用户名");
            String username = sc.next();

            System.out.println("请输入密码");
            String password = sc.next();

            //开始读取文件
            InputStream in = Class.forName("Test").getResourceAsStream("/user.xlsx");
            ReadExcel readExcel = new ReadExcel(in);
            User users[] = readExcel.readExcel(in);

            //判断用户名和密码是否正确
            int i = 0;
            for (User user : users) {
                if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                    System.out.println("登录成功");

                    InputStream inproduct=Class.forName("Test").getResourceAsStream("/products.xlsx");
                    ReadProductExcel readProductExcel = new ReadProductExcel(inproduct);
                    Product products[] = readProductExcel.getAllProduct(inproduct);
                    printProduct(products);  //打印商品信息

                    inproduct=null;
                    inproduct=Class.forName("Test").getResourceAsStream("/products.xlsx");
                    System.out.println("请输入商品ID把商品加入购物车");
                    String pId=sc.next();

                    //创建购物车数组：存的是商品
                    Product carts[]=new Product[5];
                    int count=0;

                    //根据此ID去Excel中查找商品
                    Product product=readProductExcel.getProductById(pId,inproduct);
                    if (product!=null){
                        carts[count++]=product;
                    }

                    while(true) {
                        System.out.println("继续购买商品请按1");
                        System.out.println("查看购物车请按2");

                        int choose = sc.nextInt();
                        if (choose == 1) {
                            printProduct(products);
                            inproduct = null;
                            inproduct = Class.forName("Test").getResourceAsStream("/products.xlsx");
                            System.out.println("请输入商品ID把商品加入购物车");
                            pId = sc.next();
                            product = readProductExcel.getProductById(pId, inproduct);
                            if (product != null) {
                                carts[count++] = product;
                            }
                        } else if (choose == 2) {
                            System.out.println("购物车信息");
                            printProduct(carts);
                            break;
                        }
                    }

                    i = 1;
                    bo=false;
                    break;
                }
            }
            if (i == 0) {
                System.out.println("用户名或密码错误");
            }
        }
    }
    public static void printProduct(Product products[]){
        for(Product product:products){
            if(product!=null) {
                System.out.print(product.getProductId());
                System.out.print("\t" + product.getProductName());
                System.out.print("\t" + product.getPrice());
                System.out.println("\t" + product.getDesc());
            }
        }
    }
}
