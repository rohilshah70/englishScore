import Foundation
import Combine
import shared

extension Kotlinx_coroutines_coreFlow {
    
    func asResultPublisher<T>(of: T.Type, scope: Kotlinx_coroutines_coreCoroutineScope) -> AnyPublisher<Result<T, KotlinError>, Never> {
        let flowWrapper = SwiftHelper().getFlowWrapper(scope: scope, flow: self)
        return Deferred<Publishers.HandleEvents<PassthroughSubject<Result<T, KotlinError>, Never>>> {
            let subject = PassthroughSubject<Result<T, KotlinError>, Never>()
            let job = flowWrapper.subscribe { item in
                if let item = item as? T {
                    subject.send(.success(item))
                } else {
                    let throwable = KotlinThrowable(message: "Unexpected value received from Kotlin FlowWrapper")
                    subject.send(.failure(KotlinError(throwable)))
                }
            } onComplete: {
                subject.send(completion: .finished)
            } onThrow: { error in
                subject.send(.failure(KotlinError(error)))
            }
            return subject.handleEvents(receiveCancel: {
                job.cancel(cause: nil)
            })
        }.handleEvents(receiveCancel: {
            print("Cancel publisher")
        })
        .eraseToAnyPublisher()
    }
}
