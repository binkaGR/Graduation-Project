using Newtonsoft.Json;
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
using System.Windows.Shapes;

namespace ParkingSpotAplication
{
    /// <summary>
    /// Interaction logic for ParkinSpot.xaml
    /// </summary>
    public partial class ParkinSpot : Window
    {
        private User user = new User();
        private List<ParkingSpotJson> parkingSpotJsons = new List<ParkingSpotJson>();
        private ParkingSpotJson parkinSpot = new ParkingSpotJson();
        public ParkinSpot(User _user)
        {
            InitializeComponent();
            user = _user;
            GetParkingSpot();
        }
        public void GetParkingSpot() 
        {
            string url = "http://192.168.253.130:8080/parkingspot/AllParkingSpot/username=" + user.GetUsername() + "&password=" + user.GetPassword();
            WebRequest request = WebRequest.Create(url);
            request.Method = "GET";
            request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = @reader.ReadToEnd();
            
            parkingSpotJsons = JsonConvert.DeserializeObject<List<ParkingSpotJson>>(@json);
            int freeParkSpot = 0;
            int occupateParkingSpot = 0;
            for (int i = 0; i < parkingSpotJsons.Count; i++)
            {
                lbParkingSpots.Items.Add(parkingSpotJsons[i].ParkingSpotName);
            }
            reader.Close();
            response.Close();
        }

        private void buttonDelete_Click(object sender, RoutedEventArgs e)
        {
            int deletIndex = lbParkingSpots.SelectedIndex;            
            string url = "http://192.168.253.130:8080/parkingspot/DeleteParkingSpot/"+ parkingSpotJsons[deletIndex].IdParkingSpot;
            WebRequest request = WebRequest.Create(url);
            request.Method = "DELETE";
            request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = @reader.ReadToEnd();
            MessageBox.Show(json);
            reader.Close();
            response.Close();
            GetParkingSpot();
        }

        private void ListBoxDoubleClick(object sender, EventArgs e) 
        {
            int selectIndex = lbParkingSpots.SelectedIndex;
            string url = "http://192.168.253.130:8080/parkingspot/GetParkingSpotInfo/" + parkingSpotJsons[selectIndex].IdParkingSpot;
            WebRequest request = WebRequest.Create(url);
            request.Method = "GET";
            request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = @reader.ReadToEnd();
            parkinSpot = JsonConvert.DeserializeObject<ParkingSpotJson>(@json);
            reader.Close();
            response.Close();
            lbStatus.Items.Clear();
            txtParkingSpot.Text = parkinSpot.ParkingSpotName;
            lbStatus.Items.Add(parkinSpot.StatusParkingSpot);

        } 
        
        private void buttonCreateParkingSpot_Click(object sender, RoutedEventArgs e)
        {
            string ParkingSpotStatus = lbStatus.SelectedItem.ToString();
            string ParkingSpotName = txtParkingSpot.Text;
            string url = "http://192.168.253.130:8080/parkingspot/CreateParkingSpot/" + user.GetUsername() + "/" + ParkingSpotName + "/" +ParkingSpotStatus;
            WebRequest request = WebRequest.Create(url);
            request.Method = "PUT";
            request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = @reader.ReadToEnd();
            MessageBox.Show(json);
            reader.Close();
            response.Close();
            GetParkingSpot();
        }

        private void UpdatePakringSpot_Click(object sender, RoutedEventArgs e)
        {
            int selectIndex = lbParkingSpots.SelectedIndex;
            string ParkingSpotStatus = lbStatus.SelectedItem.ToString();
            string url = "http://192.168.253.130:8080/parkingspot/UpdateSatatusParkingSpot/" + parkingSpotJsons[selectIndex].IdParkingSpot +"/"+ ParkingSpotStatus;
            WebRequest request = WebRequest.Create(url);
            request.Method = "PUT";
            request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = @reader.ReadToEnd();
            MessageBox.Show(json);
            reader.Close();
            response.Close();
        }

        private void lbStatus_SelectionChanged(object sender, EventArgs  e)
        {
            lbStatus.Items.Clear();
            lbStatus.Items.Add("availabe");
            lbStatus.Items.Add("occupied");
            lbStatus.Items.Add("repairSpot");
        }
    }
}
