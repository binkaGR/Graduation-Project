using System;
using System.Collections.Generic;
using System.Linq;
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
    /// Interaction logic for AccessManager.xaml
    /// </summary>
    public partial class AccessManager : Window
    {
        User user = new User();
        public AccessManager(User _user)
        {
            InitializeComponent();
            user = _user;
        }

        private void ButtonEmploy_Click(object sender, RoutedEventArgs e)
        {
            Еmployee еmployee = new Еmployee(user);
            еmployee.Show();
        }
        private void ButttonParking_Click(object sender, RoutedEventArgs e)
        {
            Parking parking = new Parking(user);
            parking.Show();
        }
    }
}
