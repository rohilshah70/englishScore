import Foundation
import Combine
import shared

class ContentViewModel: ObservableObject {
    private var cancellables = Set<AnyCancellable>()
    @Published var displayText = ""
    
    func getAge() {
        let scope = SwiftHelper().createScope()
        GetAgeInteractor().getAge(name: "McDonalds")
            .asResultPublisher(of: AgeDetails.self, scope: scope)
            .receive(on: DispatchQueue.main)
            .sink { result in
                SwiftHelper().clearScope(scope: scope)
                switch result {
                    case .success(let response):
                        self.displayText = String(response.age)
                    case .failure:
                        print("Failed to get age")
                }
            }
            .store(in: &cancellables)
    }
}
