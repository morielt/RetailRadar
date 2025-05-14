# RetailRadar

**From retail floor to real-time insights.**

RetailRadar is a desktop application built in Java and JavaFX that analyzes sales and inventory data from CSV files and provides clear business insights for store managers.

The project is based on my experience managing stores and training managers at JD Sports and Foot Locker, where decisions around stock, display, and promotions were often made without solid data.

---

## 🚀 Features

- **CSV Import**: Load inventory and sales data from files  
- **Best Sellers**: Shows top-selling products per brand  
- **Sales Drop**: Detects significant decline in sales rate  
- **Shelf Time**: Identifies products with no sales in the past 30 days  
- **User Interface**: JavaFX-based UI with clear buttons and feedback area  
- **Modular Logic**: Each insight is implemented via the Strategy Pattern

---

## 🧱 Tech Stack

- Java  
- JavaFX  
- FXML (UI Layout)  
- CSS (Styling)  
- Object-Oriented Design  
- Strategy Design Pattern  
- CSV Parsing

---

## 📂 Sample Files

Included in the project:

- `demo_products.csv` — Product list  
- `demo_sales.csv` — Sales records

Use the “Load CSV Files” button in the app to load both files.

---

## 🧠 How It Works

Once both CSV files are loaded:

1. Click **Best Sellers** to see the top-selling products by brand  
2. Click **Sales Drop** to identify products with declining sales (30 days vs. last 7)  
3. Click **Shelf Time** to highlight products with no sales in the last 30 days  
4. The results are displayed in a clean text output area within the application window

Each insight runs independently, and logic is separated using the Strategy Pattern for easier maintenance and future scalability.

---

## 🧑‍💻 Author

Developed by Moriel Torgeman  
Connect on [LinkedIn](https://www.linkedin.com/in/your-profile)  
GitHub: [your-github-link]

---

## 📝 License

This project is open source and available under the MIT License.
