import csv
import os

def generate_google_forms_csv(output_file='google_forms_data.csv'):
    """
    Generate a sample Google Forms CSV file with mock data.
    
    Args:
        output_file (str): Name of the output CSV file
    """
    # Define the header row
    header = [
        'Timestamp', 'Name', 'Age', 'Gender', 'Education Level', 
        'Satisfaction Score', 'Ease of Use Rating', 'Likelihood to Recommend', 
        'Favorite Feature', 'Additional Comments'
    ]
    
    # Define the data rows
    data = [
        ['2025-03-01 08:30:45', 'John Smith', '34', 'Male', 'Bachelor\'s Degree', '4', '5', '9', 'Dashboard', 'The interface is very intuitive.'],
        ['2025-03-01 09:15:22', 'Jane Doe', '28', 'Female', 'Master\'s Degree', '5', '4', '10', 'Reports', 'Excellent product! Would recommend to colleagues.'],
        ['2025-03-01 10:42:18', 'Alex Johnson', '42', 'Male', 'PhD', '3', '3', '7', 'Analytics', 'Could use more advanced filtering options.'],
        ['2025-03-01 14:27:33', 'Sarah Williams', '31', 'Female', 'Bachelor\'s Degree', '4', '4', '8', 'Dashboard', 'Very helpful for our project management.'],
        ['2025-03-02 09:05:11', 'Michael Brown', '25', 'Male', 'High School', '2', '3', '5', 'Mobile App', 'Had some issues with the mobile version.'],
        ['2025-03-02 11:18:45', 'Emily Davis', '37', 'Female', 'Master\'s Degree', '5', '5', '10', 'Reports', 'Outstanding features and very reliable.'],
        ['2025-03-02 13:42:37', 'David Wilson', '45', 'Male', 'Bachelor\'s Degree', '4', '3', '7', 'Analytics', 'Good overall but needs better documentation.'],
        ['2025-03-02 16:30:22', 'Lisa Taylor', '29', 'Female', 'Associate\'s Degree', '3', '4', '8', 'Dashboard', 'Fairly easy to use after the initial learning curve.'],
        ['2025-03-03 08:45:19', 'Robert Miller', '33', 'Male', 'Master\'s Degree', '5', '4', '9', 'Reports', 'Great product that has helped our team immensely.'],
        ['2025-03-03 10:22:41', 'Jennifer Anderson', '39', 'Female', 'Bachelor\'s Degree', '4', '5', '8', 'Mobile App', 'Love the mobile features but desktop could be improved.'],
        ['2025-03-03 14:15:33', 'James Thomas', '27', 'Male', 'High School', '3', '3', '6', 'Dashboard', 'Somewhat complex for beginners.'],
        ['2025-03-03 15:48:27', 'Michelle Martin', '36', 'Female', 'PhD', '5', '4', '9', 'Analytics', 'Very comprehensive analytics capabilities.'],
        ['2025-03-04 09:12:55', 'Christopher Lee', '41', 'Male', 'Bachelor\'s Degree', '4', '3', '7', 'Reports', 'Good reporting features but could be more customizable.'],
        ['2025-03-04 11:30:18', 'Jessica White', '30', 'Female', 'Master\'s Degree', '4', '5', '8', 'Dashboard', 'Excellent UI/UX design.'],
        ['2025-03-04 14:22:47', 'Daniel Harris', '26', 'Male', 'Associate\'s Degree', '3', '4', '7', 'Mobile App', 'Decent mobile experience but needs work.'],
        ['2025-03-04 16:45:30', 'Amanda Clark', '38', 'Female', 'Bachelor\'s Degree', '5', '4', '9', 'Analytics', 'Very powerful analytical tools.'],
        ['2025-03-05 08:33:21', 'Matthew Lewis', '32', 'Male', 'Master\'s Degree', '4', '5', '8', 'Reports', 'Well-designed reporting functionality.'],
        ['2025-03-05 10:17:39', 'Elizabeth Walker', '43', 'Female', 'PhD', '5', '3', '8', 'Dashboard', 'Robust features but slightly complex interface.'],
        ['2025-03-05 13:29:56', 'Andrew Young', '29', 'Male', 'Bachelor\'s Degree', '3', '4', '7', 'Mobile App', 'The mobile app needs performance improvements.'],
        ['2025-03-05 15:40:12', 'Olivia King', '35', 'Female', 'Master\'s Degree', '4', '4', '9', 'Analytics', 'Great analytical capabilities with good visualizations.']
    ]
    
    try:
        # Write to CSV file
        with open(output_file, 'w', newline='', encoding='utf-8') as file:
            writer = csv.writer(file)
            writer.writerow(header)
            writer.writerows(data)
        
        print(f"CSV file '{output_file}' successfully created!")
        print(f"File location: {os.path.abspath(output_file)}")
        
    except Exception as e:
        print(f"Error creating CSV file: {e}")

if __name__ == "__main__":
    generate_google_forms_csv()