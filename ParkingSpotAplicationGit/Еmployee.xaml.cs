using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Numerics;
using System.Reflection.PortableExecutable;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ParkingSpotAplication
{
    /// <summary>
    /// Interaction logic for Еmployee.xaml
    /// </summary>
    public partial class Еmployee : Window
    {
        private User user;
        List<UsersJson> usersJsons = new List<UsersJson>();
        public Еmployee(User _user)
        {
            this.user = _user;
            InitializeComponent();
            buttonCreateNewEmployee.IsEnabled = false;
            SetUsers();
        }

        public void SetUsers() 
        {
            string url = "http://192.168.253.130:8080/users/GetUsers/username=" + user.GetUsername() + "&password=" + user.GetPassword();
            WebRequest request = WebRequest.Create(url);
            request.Method = "GET";
            request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = @reader.ReadToEnd();
            usersJsons = JsonConvert.DeserializeObject<List<UsersJson>>(@json);
            for(int i=0; i<usersJsons.Count; i++) 
            {
                lb_Userslist.Items.Add(usersJsons[i].Username);
            }
            reader.Close();
            response.Close();
        }
        private void SelectUser_DoubleClick(object sender, EventArgs e) 
        {
            int index = lb_Userslist.SelectedIndex;
            txtUsername.Text = usersJsons[index].Username;
            txtPassword.Text = usersJsons[index].Password;
            lbRight.Items.Clear();
            lbRight.Items.Add(usersJsons[index].Access);
            SetUsersInformation(usersJsons[index].Username, usersJsons[index].Password);
        }

        public void SetUsersInformation(string username, string password)
        {
            string url = "http://192.168.253.130:8080/users/GetUserInformation/username=" + username + "&password=" + password;
            WebRequest request = WebRequest.Create(url);
            request.Method = "GET";
            request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = @reader.ReadToEnd();
            UserInformationJson userInformationJson = new UserInformationJson();
            userInformationJson = JsonConvert.DeserializeObject<UserInformationJson>(@json);
            txtFirstname.Clear();
            txtLastname.Clear();
            txtEmail.Clear();
            txtFirstname.Text = userInformationJson.FirstName;
            txtLastname.Text = userInformationJson.LastName;
            txtEmail.Text = userInformationJson.EmailAddress;
            reader.Close();
            response.Close();
        }

        private void Chb_AddEmployee_Checked(object sender, RoutedEventArgs e)
        {
            lbRight.Items.Clear();
            lbRight.Items.Add("manager");
            lbRight.Items.Add("employee");
            buttonCreateNewEmployee.IsEnabled = true;
        }

        private void buttonCreateNewEmployee_Click(object sender, RoutedEventArgs e)
        {
            UsersJson newUsers = new UsersJson();
            UserInformationJson newUserInformationJson  = new UserInformationJson();
            newUsers.Id = 0;
            newUsers.Username = txtUsername.Text;
            newUsers.Password = txtPassword.Text;
            newUsers.Access = lbRight.SelectedItem.ToString();
            newUserInformationJson.FirstName = txtFirstname.Text;
            newUserInformationJson.LastName = txtLastname.Text;    
            newUserInformationJson.EmailAddress = txtEmail.Text;
            
            string url = "http://192.168.253.130:8080/users/CreateUserCompany/" + user.GetUsername() + "/" + user.GetPassword() + "/" + newUsers.Username + "/" + newUsers.Password + "/" + newUsers.Access +
                            "/" + newUserInformationJson.FirstName + "/" + newUserInformationJson.LastName + "/" + newUserInformationJson.EmailAddress;
            WebRequest request = WebRequest.Create(url);
            request.Method = "GET";
            //request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = @reader.ReadToEnd();
            MessageBox.Show(json);
            reader.Close();
            response.Close();
            lb_Userslist.Items.Clear();
            SetUsers();
        }

        private void buttonChangeEmployee_Click(object sender, RoutedEventArgs e)
        {
            int indexDeleteUsers = lb_Userslist.SelectedIndex;
            UsersJson updateUsers = new UsersJson();
            UserInformationJson updateUserInformationJson = new UserInformationJson();
            updateUsers.Password = txtPassword.Text;
            updateUsers.Access = lbRight.SelectedItem.ToString();
            updateUserInformationJson.FirstName = txtFirstname.Text;
            updateUserInformationJson.LastName = txtLastname.Text;
            updateUserInformationJson.EmailAddress = txtEmail.Text;

            string url = "http://192.168.253.130:8080/users/UpdateUserInformation/" + usersJsons[indexDeleteUsers].Id +"/"+ updateUsers.Password + "/" + updateUsers.Access + "/" +
                           updateUserInformationJson.FirstName + "/" + updateUserInformationJson.LastName +"/" + updateUserInformationJson.EmailAddress;
            WebRequest request = WebRequest.Create(url);
            request.Method = "PUT";
            request.ContentLength = 0;
            //request.GetResponse();
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = reader.ReadToEnd();
            MessageBox.Show(json);
            reader.Close();
            response.Close();


        }

        private void buttonDeleteUser_Click(object sender, RoutedEventArgs e)
        {
            int indexDeleteUsers = lb_Userslist.SelectedIndex;
            string url = "http://192.168.253.130:8080/users/DeleteUsers/" + usersJsons[indexDeleteUsers].Id;
            WebRequest request = WebRequest.Create(url);
            request.Method = "DELETE";
            request.ContentLength = 0;
            //request.GetResponse();
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = reader.ReadToEnd();
            MessageBox.Show(json);
            reader.Close();
            response.Close();
            lb_Userslist.Items.Clear();
            SetUsers();
        }
    }
}
