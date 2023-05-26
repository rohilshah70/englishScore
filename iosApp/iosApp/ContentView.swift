import SwiftUI
import Combine
import shared

struct ContentView: View {
    @Environment(\.colorScheme) var colorScheme
    @StateObject var viewModel = ContentViewModel()
        
    var body: some View {
        VStack() {
            Button("Hit me for an API call") {
                viewModel.getAge()
            }
            .foregroundColor(Color.white)
            .padding()

            Text(viewModel.displayText)
                .foregroundColor(Color.white)
                .padding()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(colorScheme == .dark ? Color.darkIndigo : Color.indigoBlue)
        .edgesIgnoringSafeArea(.all)
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
