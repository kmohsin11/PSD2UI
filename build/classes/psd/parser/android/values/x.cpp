#include <bits/stdc++.h>
#define SIZE 100000
using namespace std;



int main()
{
    pair<long long, pair<int,int>> pi;
    
    cin>>n;
    vector<pi> a[n];
    int dist[n];
    for(int i=0;i<n;i++)
    dist[i]=SIZE;
    
    for(int i=0;i<n;i++){
        cin>>w>>a>>b;
        a[i].push_back(make_pair(w,make_pair(a,b)));
        cout<<a[i][0];
    }
    
    
    return 0;
}
