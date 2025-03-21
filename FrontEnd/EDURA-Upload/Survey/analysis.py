import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA
import plotly.express as px
import plotly.graph_objects as go
from plotly.subplots import make_subplots

def load_google_forms_data(file_path):
    """
    Load Google Forms data from a CSV file exported from Google Forms.
    """
    try:
        # Google Forms exports typically have a timestamp column
        df = pd.read_csv(file_path)
        print(f"Successfully loaded data with {len(df)} responses and {len(df.columns)} columns.")
        return df
    except Exception as e:
        print(f"Error loading data: {e}")
        return None

def preprocess_data(df):
    """
    Preprocess the Google Forms data:
    - Drop unnecessary columns
    - Handle missing values
    - Convert data types
    """
    # Copy the dataframe to avoid modifying the original
    df_processed = df.copy()
    
    # Rename columns if needed (Google Forms often has long question titles)
    # Example: rename columns to shorter names for easier handling
    # df_processed.columns = ['timestamp', 'age', 'gender', 'education', ...]
    
    # Drop unnecessary columns if any (e.g., email address if not needed for analysis)
    # df_processed.drop(['Email Address'], axis=1, inplace=True)
    
    # Handle missing values
    df_processed.fillna('Not Provided', inplace=True)
    
    # Convert data types if needed
    # Example: convert numeric columns to appropriate types
    # numeric_cols = ['age', 'income', 'satisfaction']
    # for col in numeric_cols:
    #     df_processed[col] = pd.to_numeric(df_processed[col], errors='coerce')
    
    return df_processed

def generate_basic_statistics(df):
    """
    Generate basic statistics for the dataset.
    """
    # Count of responses over time
    timestamps = pd.to_datetime(df.iloc[:, 0])  # Assuming first column is timestamp
    
    # Count responses by day
    daily_counts = timestamps.dt.date.value_counts().sort_index()
    
    # Basic statistics for numeric columns
    numeric_cols = df.select_dtypes(include=['int64', 'float64']).columns.tolist()
    descriptive_stats = df[numeric_cols].describe() if numeric_cols else None
    
    return {
        'daily_counts': daily_counts,
        'descriptive_stats': descriptive_stats
    }

def visualize_basic_statistics(stats):
    """
    Visualize basic statistics using matplotlib.
    """
    if 'daily_counts' in stats and not stats['daily_counts'].empty:
        plt.figure(figsize=(12, 6))
        plt.plot(stats['daily_counts'].index, stats['daily_counts'].values, marker='o')
        plt.title('Number of Responses Over Time')
        plt.xlabel('Date')
        plt.ylabel('Number of Responses')
        plt.xticks(rotation=45)
        plt.tight_layout()
        plt.savefig('responses_over_time.png')
        plt.close()

def visualize_categorical_data(df):
    """
    Visualize categorical data using various plot types.
    """
    categorical_cols = df.select_dtypes(include=['object']).columns.tolist()
    
    for i, col in enumerate(categorical_cols[1:6]):  # Skip timestamp, limit to 5 columns
        plt.figure(figsize=(10, 6))
        
        # Get value counts and sort
        value_counts = df[col].value_counts().sort_values(ascending=False)
        
        # If there are too many categories, limit to top 10
        if len(value_counts) > 10:
            value_counts = value_counts.head(10)
            title_suffix = " (Top 10)"
        else:
            title_suffix = ""
        
        # Create bar plot
        ax = value_counts.plot(kind='bar')
        plt.title(f'Distribution of {col}{title_suffix}')
        plt.ylabel('Count')
        plt.xlabel(col)
        plt.xticks(rotation=45, ha='right')
        
        # Add value labels on top of each bar
        for p in ax.patches:
            ax.annotate(str(int(p.get_height())), 
                        (p.get_x() + p.get_width() / 2., p.get_height()),
                        ha='center', va='bottom')
        
        plt.tight_layout()
        plt.savefig(f'category_distribution_{i}.png')
        plt.close()

def visualize_likert_scale_data(df, likert_columns):
    """
    Visualize Likert scale questions using a heatmap.
    """
    if not likert_columns:
        return
    
    # Get the data for the Likert scale questions
    likert_data = df[likert_columns]
    
    # Create a DataFrame with the counts for each response option
    likert_counts = pd.DataFrame()
    for col in likert_columns:
        likert_counts[col] = likert_data[col].value_counts().sort_index()
    
    # Create a heatmap
    plt.figure(figsize=(12, 8))
    sns.heatmap(likert_counts, annot=True, cmap='YlGnBu', fmt='d')
    plt.title('Responses to Likert Scale Questions')
    plt.xlabel('Question')
    plt.ylabel('Response Option')
    plt.tight_layout()
    plt.savefig('likert_scale_heatmap.png')
    plt.close()

def create_correlation_matrix(df):
    """
    Create a correlation matrix for numeric columns.
    """
    # Select numeric columns
    numeric_cols = df.select_dtypes(include=['int64', 'float64']).columns.tolist()
    
    if len(numeric_cols) < 2:
        return
    
    # Calculate correlation matrix
    corr_matrix = df[numeric_cols].corr()
    
    # Create a heatmap
    plt.figure(figsize=(10, 8))
    sns.heatmap(corr_matrix, annot=True, cmap='coolwarm', vmin=-1, vmax=1, center=0)
    plt.title('Correlation Matrix')
    plt.tight_layout()
    plt.savefig('correlation_matrix.png')
    plt.close()

def perform_pca_analysis(df):
    """
    Perform PCA analysis on numeric data.
    """
    # Select numeric columns
    numeric_cols = df.select_dtypes(include=['int64', 'float64']).columns.tolist()
    
    if len(numeric_cols) < 3:
        return
    
    # Extract numeric data
    numeric_data = df[numeric_cols].dropna()
    
    # Standardize the data
    scaler = StandardScaler()
    scaled_data = scaler.fit_transform(numeric_data)
    
    # Perform PCA
    pca = PCA()
    pca_result = pca.fit_transform(scaled_data)
    
    # Create a scree plot
    plt.figure(figsize=(10, 6))
    plt.plot(range(1, len(pca.explained_variance_ratio_) + 1), 
             pca.explained_variance_ratio_, marker='o')
    plt.title('Scree Plot')
    plt.xlabel('Principal Component')
    plt.ylabel('Variance Explained')
    plt.grid(True)
    plt.savefig('pca_scree_plot.png')
    plt.close()
    
    # Create a biplot for the first two components
    plt.figure(figsize=(10, 8))
    plt.scatter(pca_result[:, 0], pca_result[:, 1], alpha=0.5)
    plt.title('PCA Biplot')
    plt.xlabel(f'PC1 ({pca.explained_variance_ratio_[0]:.2%} variance)')
    plt.ylabel(f'PC2 ({pca.explained_variance_ratio_[1]:.2%} variance)')
    
    # Add feature vectors
    feature_vectors = pca.components_.T
    for i, feature in enumerate(numeric_cols):
        plt.arrow(0, 0, feature_vectors[i, 0], feature_vectors[i, 1], 
                  head_width=0.05, head_length=0.05, fc='red', ec='red')
        plt.text(feature_vectors[i, 0] * 1.15, feature_vectors[i, 1] * 1.15, 
                 feature, color='red')
    
    plt.grid(True)
    plt.tight_layout()
    plt.savefig('pca_biplot.png')
    plt.close()

def create_interactive_dashboard(df):
    """
    Create an interactive dashboard using Plotly.
    """
    # Create a subplot figure
    fig = make_subplots(
        rows=2, cols=2,
        subplot_titles=('Response Distribution', 'Response Timeline', 
                        'Categorical Distribution', 'Numeric Distribution'),
        specs=[[{"type": "bar"}, {"type": "line"}],
               [{"type": "pie"}, {"type": "histogram"}]]
    )
    
    # Response Distribution (Bar Chart)
    # Assuming the second column is a categorical question
    if len(df.columns) > 1:
        categorical_col = df.columns[1]
        value_counts = df[categorical_col].value_counts().sort_values(ascending=False)
        fig.add_trace(
            go.Bar(x=value_counts.index, y=value_counts.values),
            row=1, col=1
        )
    
    # Response Timeline (Line Chart)
    # Assuming the first column is the timestamp
    timestamps = pd.to_datetime(df.iloc[:, 0])
    daily_counts = timestamps.dt.date.value_counts().sort_index()
    fig.add_trace(
        go.Scatter(x=daily_counts.index, y=daily_counts.values, mode='lines+markers'),
        row=1, col=2
    )
    
    # Categorical Distribution (Pie Chart)
    # Assuming the third column is another categorical question
    if len(df.columns) > 2:
        categorical_col_2 = df.columns[2]
        value_counts_2 = df[categorical_col_2].value_counts()
        fig.add_trace(
            go.Pie(labels=value_counts_2.index, values=value_counts_2.values),
            row=2, col=1
        )
    
    # Numeric Distribution (Histogram)
    # Find a numeric column for the histogram
    numeric_cols = df.select_dtypes(include=['int64', 'float64']).columns.tolist()
    if numeric_cols:
        fig.add_trace(
            go.Histogram(x=df[numeric_cols[0]]),
            row=2, col=2
        )
    
    # Update layout
    fig.update_layout(height=800, width=1000, title_text="Interactive Dashboard")
    
    # Save the figure as an HTML file
    fig.write_html("interactive_dashboard.html")

def create_sunburst_chart(df, cat_cols):
    """
    Create a sunburst chart for hierarchical categorical data.
    """
    if len(cat_cols) < 2 or not all(col in df.columns for col in cat_cols):
        return
    
    # Create a sunburst chart
    fig = px.sunburst(
        df, 
        path=cat_cols,
        title='Hierarchical View of Categories'
    )
    
    fig.update_layout(width=800, height=800)
    fig.write_html("sunburst_chart.html")

def main():
    # File path for the Google Forms data
    file_path = 'google_forms_data.csv'  # Replace with your file path
    
    # Load the data
    df = load_google_forms_data(file_path)
    
    if df is None:
        print("Failed to load data. Exiting.")
        return
    
    # Preprocess the data
    df_processed = preprocess_data(df)
    
    # Generate and visualize basic statistics
    stats = generate_basic_statistics(df_processed)
    visualize_basic_statistics(stats)
    
    # Visualize categorical data
    visualize_categorical_data(df_processed)
    
    # Example: Visualize Likert scale data (replace with your actual Likert columns)
    # likert_columns = ['Satisfaction', 'Ease of Use', 'Likelihood to Recommend']
    # visualize_likert_scale_data(df_processed, likert_columns)
    
    # Create correlation matrix
    create_correlation_matrix(df_processed)
    
    # Perform PCA analysis
    perform_pca_analysis(df_processed)
    
    # Create interactive dashboard
    create_interactive_dashboard(df_processed)
    
    # Example: Create sunburst chart for hierarchical data
    # cat_cols = ['Category1', 'Category2', 'Category3']
    # create_sunburst_chart(df_processed, cat_cols)
    
    print("Analysis complete. Check the generated visualizations.")

if __name__ == "__main__":
    main()