using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ParkingSpotAplication
{
    public class User
    {
        private string Username = "demo";
        private string Password = "demo";

        private string Access = "demo";

        public void SetUsername(string _username)
        {
            this.Username = _username;
        }
        public string GetUsername()
        {
            return this.Username;
        }

        public void SetPassword(string _password)
        {
            this.Password = _password;
        }
        public string GetPassword()
        {
            return this.Password;
        }

        public void SetAccess(string _access)
        {
            this.Access = _access;
        }

        public string GetAccess()
        {
            return this.Access;
        }
    }
}
