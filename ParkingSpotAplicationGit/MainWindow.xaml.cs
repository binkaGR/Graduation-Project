using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ParkingSpotAplication
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        User user = new User();

        public MainWindow()
        {
            InitializeComponent();
        }
        private void ButtonLoggin_Click(object sender, RoutedEventArgs e)
        {
            string GetAccessResult;
            user.SetUsername( txtUsername.Text.ToString());
            user.SetPassword(txtPassword.Password.ToString());
            string url = "http://192.168.253.130:8080/users/getAccess/username=" + user.GetUsername() + "&password=" + user.GetPassword();
            WebRequest request = WebRequest.Create(url);
            request.Method = "GET";
            request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string serverRespond = reader.ReadToEnd();
            GetAccessResult = serverRespond;

            //GetAccessResult = System.Text.Json.JsonSerializer.Deserialize<string>(json);
            reader.Close();
            response.Close();
            user.SetAccess(GetAccessResult);
            switch (GetAccessResult)
            {
                case "manager":
                    {
                        AccessManager accessManager = new AccessManager(user);
                        accessManager.Show();
                        break;
                    }
                case "employee":
                    {
                        Parking parking = new Parking(user);
                        parking.Show();
                        break;
                    }
                case "Access denied!":
                    {

                        MessageBox.Show("GetAccessResult");
                        break;
                    }
            }

        }
    }
}
