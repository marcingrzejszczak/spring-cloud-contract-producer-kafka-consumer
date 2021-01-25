import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description('should send an fx rate')

    label('triggerJsonFxRateEvent')
    input {
        triggeredBy('triggerJsonFxRateEvent()')
    }
    outputMessage {
        sentTo('json-fx-rate-events')
        body(
          
                [
                    
	                from: 'GBP',
	                to: 'USD',
	                rate: '1.23'
                    
                ]
            
        	
        )
    }
}

