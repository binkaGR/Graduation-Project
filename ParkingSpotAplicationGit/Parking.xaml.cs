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
using System.Text.Json;
using System.Net;
using Newtonsoft.Json;
namespace ParkingSpotAplication
{
    /// <summary>
    /// Interaction logic for Parking.xaml
    /// </summary>
    public partial class Parking : Window
    {
        User user = new User();

        public Parking(User _user)
        {
            InitializeComponent();
            user = _user;
            if (user.GetAccess().Equals("employee"))
            {
                ButtonAddParkingSpot.IsEnabled = false;
            }
            GetParkingSpot();

        }
        public void GetParkingSpot()
        {
            string url = "http://192.168.253.130:8080/parkingspot/AllParkingSpot/username="+user.GetUsername()+"&password="+ user.GetPassword();
            WebRequest request = WebRequest.Create(url);
            request.Method = "GET";
            request.ContentLength = 0;
            WebResponse response = request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string json = @reader.ReadToEnd();
            List<ParkingSpotJson> parkingSpotJsons = new List<ParkingSpotJson>();
            parkingSpotJsons = JsonConvert.DeserializeObject<List<ParkingSpotJson>>(@json);
            int freeParkSpot = 0;
            int occupateParkingSpot = 0;
            for (int i = 0; i < parkingSpotJsons.Count; i++)
            {
                if (parkingSpotJsons[i].StatusParkingSpot.Equals("availabe")) 
                {
                    lb_ParkingSpotFree.Items.Add(parkingSpotJsons[i].ParkingSpotName);
                    freeParkSpot++;
                }
                if (parkingSpotJsons[i].StatusParkingSpot.Equals("occupied"))
                {
                    lb_ParkingSpotOccupate.Items.Add(parkingSpotJsons[i].ParkingSpotName);
                    occupateParkingSpot++;
                }

            }
            txtblock_freeParkingSpot.Text = freeParkSpot.ToString();
            txtBlock_occupateParkingSpot.Text = occupateParkingSpot.ToString();
            reader.Close();
            response.Close();
        }

        private void ButtonAddParkingSpot_Click(object sender, RoutedEventArgs e)
        {
            ParkinSpot parkinSpot = new ParkinSpot(user);
            parkinSpot.Show();
        }
    }
}
