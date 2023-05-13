using System.Diagnostics;
using System.Text.RegularExpressions;
using static System.Net.Mime.MediaTypeNames;

internal class Program
{
    private static void Main(string[] args)
    {
        Console.WriteLine("Hello, World!");


        Process process = new Process();
        process.StartInfo.FileName = "cmd.exe";
        
        process.StartInfo.UseShellExecute = false;
        process.StartInfo.RedirectStandardOutput = true;



        for (int i = 1; i <= 25; i++)
        {

            string directoryPath = "D:\\DaneOut\\" + i + "\\p";
            string accountTestPath = "java -cp \"" + directoryPath + ";C:\\Junit\\junit-4.12.jar;C:\\Junit\\hamcrest-core-1.3.jar\" org.junit.runner.JUnitCore com.uj.atm.Test.AccountTest";
            string creditCardTestPath = "java -cp \"" + directoryPath + ";C:\\Junit\\junit-4.12.jar;C:\\Junit\\hamcrest-core-1.3.jar\" org.junit.runner.JUnitCore com.uj.atm.Test.CreditCardTest";
            string atmTestPath = "java -cp \"" + directoryPath + ";C:\\Junit\\junit-4.12.jar;C:\\Junit\\hamcrest-core-1.3.jar\" org.junit.runner.JUnitCore com.uj.atm.Test.AtmTest";

            Console.WriteLine(accountTestPath);
            Console.WriteLine(creditCardTestPath);
            Console.WriteLine(atmTestPath);

            FileSaver(ConsoleFormater(process, accountTestPath), $"com.uj.atm.Test.AccountTest;{i};");
            FileSaver(ConsoleFormater(process, creditCardTestPath), $"com.uj.atm.Test.CreditCardTest;{i};");
            FileSaver(ConsoleFormater(process, atmTestPath), $"com.uj.atm.Test.AtmTest;{i};");

            for (int j = 1; j <= 25; j++)
            {
                if (i != j)
                {
                    directoryPath = "D:\\DaneOut\\" + i + "_" + j + "\\p";
                    accountTestPath = "java -cp \"" + directoryPath + ";C:\\Junit\\junit-4.12.jar;C:\\Junit\\hamcrest-core-1.3.jar\" org.junit.runner.JUnitCore com.uj.atm.Test.AccountTest";
                    creditCardTestPath = "java -cp \"" + directoryPath + ";C:\\Junit\\junit-4.12.jar;C:\\Junit\\hamcrest-core-1.3.jar\" org.junit.runner.JUnitCore com.uj.atm.Test.CreditCardTest";
                    atmTestPath = "java -cp \"" + directoryPath + ";C:\\Junit\\junit-4.12.jar;C:\\Junit\\hamcrest-core-1.3.jar\" org.junit.runner.JUnitCore com.uj.atm.Test.AtmTest";
                    Console.WriteLine(accountTestPath);
                    Console.WriteLine(creditCardTestPath);
                    Console.WriteLine(atmTestPath);
                    FileSaver(ConsoleFormater(process, accountTestPath), $"com.uj.atm.Test.AccountTest;{i}_{j};");
                    FileSaver(ConsoleFormater(process, creditCardTestPath), $"com.uj.atm.Test.CreditCardTest;{i}_{j};");
                    FileSaver(ConsoleFormater(process, atmTestPath), $"com.uj.atm.Test.AtmTest;{i}_{j};");


                    //File.AppendAllText("output.txt", output);
                }
              
            }
        }

        static IEnumerable<string> ConsoleFormater(Process process, string command)
        {
            process.StartInfo.Arguments = "/c " + command; // "/c" option is used to close the command prompt after the command is executed                                         
            process.Start();// Start the process
            string output = process.StandardOutput.ReadToEnd();  // Read the output of the command and write it to a file

            string pattern = @"^Tests run:.*$";
            string patter2 = @"^OK.*$"; 
            string[] lines1 = output.Split('\n').Where(line => Regex.IsMatch(line, pattern)).ToArray(); // Split the input string into lines and filter out lines that don't match the pattern
            string[] lines2 = output.Split('\n').Where(line => Regex.IsMatch(line, patter2)).ToArray();
            string[] lines = lines1.Union(lines2).ToArray();
            foreach (string line in lines)  // Print the matching lines
            {            
                yield return line;
            }
            process.WaitForExit();
        }

        static void FileSaver(IEnumerable<string> lines, string anotations)
        {
            foreach (string line in lines)  // Print the matching lines
            {
                Console.WriteLine(line);
                File.AppendAllText("output.txt", $"{anotations}{line}");
            }
        }

        static string RemoveJavaLang(string input)
        {
            int startIndex = input.IndexOf("java.lang");
            if (startIndex < 0)
            {
                // "java.lang" not found, return input as-is
                return input;
            }

            int endIndex = input.IndexOf("more", startIndex);
            if (endIndex < 0)
            {
                // "more" not found after "java.lang", return input as-is
                return input;
            }

            // Return the substring before "java.lang" and after "more"
            return input.Substring(0, startIndex) + input.Substring(endIndex + 4);
        }

    }
}